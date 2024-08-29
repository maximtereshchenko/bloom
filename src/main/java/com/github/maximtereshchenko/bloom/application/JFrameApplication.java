package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;

import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

final class JFrameApplication extends JFrame implements AutoCloseable {

    private final BloomModule module;
    private final JPanelDisplay display;
    private final ScheduledExecutorService executorService;

    private JFrameApplication(BloomModule module, JPanelDisplay display, KeyListenerKeypad keypad) {
        this.module = module;
        this.display = display;
        this.executorService = Executors.newScheduledThreadPool(4);
        setTitle("Bloom - CHIP-8 interpreter");
        getContentPane().add(display);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new CleanUp(executorService));
        addKeyListener(keypad);
    }

    static JFrameApplication from(String location) throws Exception {
        return from(location, event -> {});
    }

    static JFrameApplication from(String location, LineListener lineListener) throws Exception {
        var keypad = new KeyListenerKeypad();
        var display = new JPanelDisplay();
        return new JFrameApplication(
            new BloomFacade(
                program(location),
                display,
                keypad,
                ClipSound.configured(lineListener),
                new TrueRandomness()
            ),
            display,
            keypad
        );
    }

    private static byte[] program(String location) throws IOException, InterruptedException {
        try {
            return Files.readAllBytes(Paths.get(location));
        } catch (IOException e) {
            return program(URI.create(location));
        }
    }

    private static byte[] program(URI uri) throws IOException, InterruptedException {
        try (var httpClient = httpClient()) {
            return httpClient.send(
                    HttpRequest.newBuilder()
                        .GET()
                        .uri(uri)
                        .build(),
                    HttpResponse.BodyHandlers.ofByteArray()
                )
                .body();
        }
    }

    private static HttpClient httpClient() {
        return HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
    }

    @Override
    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    void start() {
        runAtFrequency(new FunctionalTask(module::executeNextOperation), 700);
        runAtFrequency(new FunctionalTask(module::decrementDelayTimer), 60);
        runAtFrequency(new FunctionalTask(module::decrementSoundTimer), 60);
        runAtFrequency(new DisplayUpdateTask(display), 60);
    }

    private void runAtFrequency(LoggingTask task, int hertz) {
        executorService.scheduleAtFixedRate(
            task,
            0,
            TimeUnit.SECONDS.toMicros(1) / hertz,
            TimeUnit.MICROSECONDS
        );
    }

    private static final class CleanUp extends WindowAdapter {

        private final ExecutorService executorService;

        CleanUp(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            try {
                executorService.shutdown();
                if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

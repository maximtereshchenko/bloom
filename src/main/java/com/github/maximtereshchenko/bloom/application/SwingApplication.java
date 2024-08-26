package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.ExecuteNextOperationUseCase;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

final class SwingApplication extends JFrame implements AutoCloseable {

    private final ExecuteNextOperationUseCase useCase;
    private final SwingDisplay display;
    private final ScheduledExecutorService executorService;

    private SwingApplication(ExecuteNextOperationUseCase useCase, SwingDisplay display) {
        this.useCase = useCase;
        this.display = display;
        this.executorService = Executors.newScheduledThreadPool(2);
        setTitle("Bloom - CHIP-8 interpreter");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        getContentPane().add(display);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new CleanUp(executorService));
    }

    static SwingApplication from(Path path) throws IOException {
        var module = new BloomFacade(Files.readAllBytes(path));
        return new SwingApplication(module, SwingDisplay.from(module));
    }

    @Override
    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    void start() {
        runAtFrequency(new ExecuteNextOperationTask(useCase), 700);
        runAtFrequency(new RepaintTask(display), 60);
    }

    private void runAtFrequency(Runnable runnable, int hertz) {
        executorService.scheduleAtFixedRate(
            runnable,
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
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

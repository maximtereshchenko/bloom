package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.ExecuteNextInstructionUseCase;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

final class Application implements AutoCloseable {

    private final ExecuteNextInstructionUseCase useCase;
    private final TerminalDisplay display;
    private final PrintStream printStream;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    Application(ExecuteNextInstructionUseCase useCase, TerminalDisplay display, PrintStream printStream) {
        this.useCase = useCase;
        this.display = display;
        this.printStream = printStream;
    }

    static Application from(PrintStream printStream, Path program) throws IOException {
        var module = new BloomFacade(Files.readAllBytes(program));
        return new Application(module, new TerminalDisplay(printStream, module), printStream);
    }

    @Override
    public void close() throws InterruptedException {
        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
        display.close();
    }

    void start() {
        runAtFrequency(useCase::executeNextInstruction, 700);
        runAtFrequency(display::draw, 60);
    }

    private void runAtFrequency(Runnable runnable, int hertz) {
        executorService.scheduleAtFixedRate(
            () -> printException(runnable),
            0,
            TimeUnit.SECONDS.toMicros(1) / hertz,
            TimeUnit.MICROSECONDS
        );
    }

    private void printException(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            executorService.shutdown();
            e.printStackTrace(printStream);
            throw e;
        }
    }
}

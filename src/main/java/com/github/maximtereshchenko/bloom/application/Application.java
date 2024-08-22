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

final class Application {

    private final ExecuteNextInstructionUseCase useCase;
    private final TerminalDisplay display;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    Application(ExecuteNextInstructionUseCase useCase, TerminalDisplay display) {
        this.useCase = useCase;
        this.display = display;
    }

    static Application from(PrintStream printStream, Path program) throws IOException {
        var module = new BloomFacade(Files.readAllBytes(program));
        return new Application(module, new TerminalDisplay(printStream, module));
    }

    void start() {
        executorService.scheduleAtFixedRate(
            useCase::executeNextInstruction,
            0,
            TimeUnit.SECONDS.toMicros(1) / 60,
            TimeUnit.MICROSECONDS
        );
        executorService.scheduleAtFixedRate(
            display::draw,
            0,
            TimeUnit.SECONDS.toMicros(1) / 60,
            TimeUnit.MICROSECONDS
        );
    }

    void stop() throws InterruptedException {
        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }
}

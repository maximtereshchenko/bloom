package com.github.maximtereshchenko.bloom.application;

final class FunctionalTask extends LoggingTask {

    private final Runnable runnable;

    FunctionalTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    void execute() {
        runnable.run();
    }
}

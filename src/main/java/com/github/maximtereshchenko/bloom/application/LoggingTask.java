package com.github.maximtereshchenko.bloom.application;

abstract class LoggingTask implements Runnable {

    @Override
    public void run() {
        try {
            execute();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IllegalStateException(t);
        }
    }

    abstract void execute() throws Throwable;
}

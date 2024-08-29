package com.github.maximtereshchenko.bloom.application;

final class DisplayUpdateTask extends LoggingTask {

    private final JPanelDisplay display;

    DisplayUpdateTask(JPanelDisplay display) {
        this.display = display;
    }

    @Override
    void execute() throws Throwable {
        display.update();
    }
}

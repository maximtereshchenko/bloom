package com.github.maximtereshchenko.bloom.application;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

final class RepaintTask extends LoggingTask {

    private final Component component;

    RepaintTask(Component component) {
        this.component = component;
    }

    @Override
    void execute() throws Throwable {
        try {
            SwingUtilities.invokeAndWait(component::repaint);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}

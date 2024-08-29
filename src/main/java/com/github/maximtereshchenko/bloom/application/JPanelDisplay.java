package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.port.Display;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

final class JPanelDisplay extends JPanel implements Display {

    private final BlockingQueue<boolean[][]> queue = new SynchronousQueue<>();
    private boolean[][] previousMask = new boolean[0][0];

    JPanelDisplay() {
        setPreferredSize(new Dimension(640, 320));
    }

    @Override
    public void draw(boolean[][] mask) {
        try {
            queue.put(mask);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        var mask = queue.poll();
        if (mask == null) {
            paint(graphics, previousMask);
        } else {
            paint(graphics, mask);
            previousMask = mask;
        }
    }

    private void paint(Graphics graphics, boolean[][] mask) {
        if (mask.length == 0) {
            return;
        }
        var pixelHeight = getHeight() / mask.length;
        for (var row = 0; row < mask.length; row++) {
            var pixelWidth = getWidth() / mask[row].length;
            for (var column = 0; column < mask[row].length; column++) {
                graphics.setColor(mask[row][column] ? Color.WHITE : Color.BLACK);
                graphics.fillRect(
                    column * pixelWidth,
                    row * pixelHeight,
                    pixelWidth,
                    pixelHeight
                );
            }
        }
    }
}

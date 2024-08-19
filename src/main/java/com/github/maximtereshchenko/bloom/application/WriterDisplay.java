package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.Display;
import java.io.IOException;
import java.io.Writer;

final class WriterDisplay implements Display {

    static final int WIDTH = 64;
    static final int HEIGHT = 32;

    private final Writer writer;
    private final byte[][] pixels = new byte[HEIGHT][WIDTH];
    private boolean isEmpty = true;

    WriterDisplay(Writer writer) {
        this.writer = writer;
    }

    @Override
    public int width() {
        return WIDTH;
    }

    @Override
    public int height() {
        return HEIGHT;
    }

    @Override
    public boolean isPixelEnabled(int row, int column) {
        return pixels[row][column] == 1;
    }

    @Override
    public void flipPixel(int row, int column) {
        var current = pixels[row][column];
        pixels[row][column] = (byte) (current == 0 ? 1 : 0);
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var row : pixels) {
            for (var value : row) {
                builder.append(value == 1 ? '*' : ' ');
            }
            builder.append(System.lineSeparator());
        }
        return builder.substring(0, builder.length() - 1);
    }

    void draw() throws IOException {
        if (!isEmpty) {
            clear();
        }
        writer.write(toString());
        isEmpty = false;
    }

    /**
     * Clear entire line. Cursor position does not change.
     */
    private void clear() throws IOException {
        writer.write("\033[2K".repeat(HEIGHT));
    }
}

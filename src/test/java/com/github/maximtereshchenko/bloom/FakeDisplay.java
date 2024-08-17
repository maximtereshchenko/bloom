package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.Display;

final class FakeDisplay implements Display {

    private final byte[][] pixels = new byte[height()][width()];

    @Override
    public int width() {
        return 64;
    }

    @Override
    public int height() {
        return 32;
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
        return builder.toString();
    }
}

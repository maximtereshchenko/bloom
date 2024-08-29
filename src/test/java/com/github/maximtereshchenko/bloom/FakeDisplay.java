package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.port.Display;

final class FakeDisplay implements Display {

    private boolean[][] mask = new boolean[0][0];

    @Override
    public void draw(boolean[][] mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var row : mask) {
            for (var isPixelEnabled : row) {
                builder.append(isPixelEnabled ? '*' : ' ');
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}

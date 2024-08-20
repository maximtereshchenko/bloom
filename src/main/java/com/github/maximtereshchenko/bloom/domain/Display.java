package com.github.maximtereshchenko.bloom.domain;

final class Display {

    static final int HEIGHT = 32;
    static final int WIDTH = 64;

    private final boolean[][] pixels = new boolean[HEIGHT][WIDTH];

    void flipPixel(int row, int column) {
        pixels[row][column] = !pixels[row][column];
    }

    boolean isPixelEnabled(int row, int column) {
        return pixels[row][column];
    }

    boolean[][] mask() {
        return pixels.clone();
    }
}

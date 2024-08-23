package com.github.maximtereshchenko.bloom.domain;

final class Display {

    static final UnsignedByte HEIGHT = UnsignedByte.from(32);
    static final UnsignedByte WIDTH = UnsignedByte.from(64);

    private final boolean[][] pixels = new boolean[HEIGHT.primitive()][WIDTH.primitive()];

    void flipPixel(UnsignedByte row, UnsignedByte column) {
        pixels[row.primitive()][column.primitive()] = !pixels[row.primitive()][column.primitive()];
    }

    boolean isPixelEnabled(UnsignedByte row, UnsignedByte column) {
        return pixels[row.primitive()][column.primitive()];
    }

    boolean[][] mask() {
        var clone = pixels.clone();
        for (int i = 0; i < clone.length; i++) {
            clone[i] = pixels[i].clone();
        }
        return clone;
    }
}

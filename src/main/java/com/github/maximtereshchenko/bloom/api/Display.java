package com.github.maximtereshchenko.bloom.api;

public interface Display {

    boolean isPixelEnabled(int row, int column);

    void flipPixel(int row, int column);
}

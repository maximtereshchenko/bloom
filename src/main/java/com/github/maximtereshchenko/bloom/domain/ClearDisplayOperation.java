package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

/**
 * It should clear the display, turning all pixels off to 0.
 */
final class ClearDisplayOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Display display) {
        for (var row = 0; row < display.height(); row++) {
            for (var column = 0; column < display.width(); column++) {
                if (display.isPixelEnabled(row, column)) {
                    display.flipPixel(row, column);
                }
            }
        }
    }
}

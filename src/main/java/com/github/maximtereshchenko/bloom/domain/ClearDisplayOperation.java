package com.github.maximtereshchenko.bloom.domain;

/**
 * 00E0 - CLS. Clear the display.
 */
final class ClearDisplayOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        for (var row = 0; row < Display.HEIGHT; row++) {
            for (var column = 0; column < Display.WIDTH; column++) {
                if (display.isPixelEnabled(row, column)) {
                    display.flipPixel(row, column);
                }
            }
        }
    }
}

package com.github.maximtereshchenko.bloom.domain;

/**
 * 00E0 - CLS. Clear the display.
 */
final class ClearDisplayOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var zero = new UnsignedByte();
        for (var row : zero.rangeTo(Display.HEIGHT)) {
            for (var column : zero.rangeTo(Display.WIDTH)) {
                if (display.isPixelEnabled(row, column)) {
                    display.flipPixel(row, column);
                }
            }
        }
    }
}

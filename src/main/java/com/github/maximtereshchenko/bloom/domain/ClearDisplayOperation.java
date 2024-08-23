package com.github.maximtereshchenko.bloom.domain;

/**
 * 00E0 - CLS. Clear the display.
 */
final class ClearDisplayOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var rowIterator = new UnsignedByte().rangeTo(Display.HEIGHT);
        while (rowIterator.hasNext()) {
            var row = rowIterator.next();
            var columnIterator = new UnsignedByte().rangeTo(Display.WIDTH);
            while (columnIterator.hasNext()) {
                var column = columnIterator.next();
                if (display.isPixelEnabled(row, column)) {
                    display.flipPixel(row, column);
                }
            }
        }
    }
}

package com.github.maximtereshchenko.bloom.domain;

/**
 * 00E0 - CLS. Clear the display.
 */
final class ClearDisplayOperation implements Operation {

    private final Display display;

    ClearDisplayOperation(Display display) {
        this.display = display;
    }

    @Override
    public void execute() {
        for (var row : UnsignedByte.ZERO.rangeTo(Display.HEIGHT)) {
            for (var column : UnsignedByte.ZERO.rangeTo(Display.WIDTH)) {
                if (display.isPixelEnabled(row, column)) {
                    display.flipPixel(row, column);
                }
            }
        }
    }
}

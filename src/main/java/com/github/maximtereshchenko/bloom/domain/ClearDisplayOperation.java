package com.github.maximtereshchenko.bloom.domain;

/**
 * 00E0 - CLS. Clear the display.
 */
final class ClearDisplayOperation implements Operation {

    private final StagingDisplay display;

    ClearDisplayOperation(StagingDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        for (var row : UnsignedByte.ZERO.rangeTo(StagingDisplay.HEIGHT)) {
            for (var column : UnsignedByte.ZERO.rangeTo(StagingDisplay.WIDTH)) {
                if (display.isPixelEnabled(row, column)) {
                    display.flipPixel(row, column);
                }
            }
        }
        display.draw();
    }
}

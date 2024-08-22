package com.github.maximtereshchenko.bloom.domain;

/**
 * It will draw an N pixels tall sprite from the memory location that the I index register is holding to the screen, at
 * the horizontal X coordinate in VX and the Y coordinate in VY. All the pixels that are “on” in the sprite will flip
 * the pixels on the screen that it is drawn to (from left to right, from most to least significant bit). If any pixels
 * on the screen were turned “off” by this, the VF flag register is set to 1. Otherwise, it’s set to 0. The starting
 * position of the sprite will wrap. In other words, an X coordinate of 5 is the same as an X of 68 (since the screen is
 * 64 pixels wide). However, the actual drawing of the sprite should not wrap. If a sprite is drawn near the edge of the
 * screen, it should be clipped, and not wrap.
 */
final class DisplayOperation implements Operation {

    private final HexadecimalSymbol startRowRegister;
    private final HexadecimalSymbol startColumnRegister;
    private final int rows;

    DisplayOperation(HexadecimalSymbol startRowRegister, HexadecimalSymbol startColumnRegister, int rows) {
        this.startRowRegister = startRowRegister;
        this.startColumnRegister = startColumnRegister;
        this.rows = rows;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var memoryAddress = registers.index().get();
        var flagRegister = registers.flagRegister();
        var startRow = registers.generalPurpose(startRowRegister).get().primitive() % Display.HEIGHT;
        var startColumn = registers.generalPurpose(startColumnRegister).get().primitive() % Display.WIDTH;
        flagRegister.disable();
        for (var row = startRow; row < startRow + rows; row++, memoryAddress = memoryAddress.next()) {
            displayRow(display, randomAccessMemory.value(memoryAddress), row, startColumn, flagRegister);
        }
    }

    private void displayRow(Display display, Byte bits, int row, int startColumn, FlagRegister flagRegister) {
        for (int column = startColumn, bitIndex = 0; bitIndex < Byte.LENGTH; column++, bitIndex++) {
            if (row < Display.HEIGHT && column < Display.WIDTH && bits.has(bitIndex)) {
                display.flipPixel(row, column);
                if (!display.isPixelEnabled(row, column)) {
                    flagRegister.enable();
                }
            }
        }
    }
}

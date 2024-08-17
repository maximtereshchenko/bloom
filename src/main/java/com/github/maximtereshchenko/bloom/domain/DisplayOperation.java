package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

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

    private final Display display;
    private final HexadecimalSymbol startRowRegister;
    private final HexadecimalSymbol startColumnRegister;
    private final int rows;

    DisplayOperation(
        Display display,
        HexadecimalSymbol startRowRegister,
        HexadecimalSymbol startColumnRegister,
        int rows
    ) {
        this.display = display;
        this.startRowRegister = startRowRegister;
        this.startColumnRegister = startColumnRegister;
        this.rows = rows;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory) {
        var memoryAddress = registers.index().value();
        var startRow = registers.generalPurpose(startRowRegister).value().value();
        for (var row = startRow; row < startRow + rows; row++, memoryAddress = memoryAddress.next()) {
            displayRow(
                randomAccessMemory.value(memoryAddress),
                row,
                registers.generalPurpose(startColumnRegister).value().value()
            );
        }
    }

    private void displayRow(Byte bits, byte row, byte startColumn) {
        for (int column = startColumn, bitIndex = 0; bitIndex < Byte.LENGTH; column++, bitIndex++) {
            if (bits.has(bitIndex)) {
                display.flipPixel(row, column);
            }
        }
    }
}

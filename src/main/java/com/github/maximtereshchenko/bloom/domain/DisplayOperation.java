package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

final class DisplayOperation implements Operation {

    private final Display display;
    private final int startRow;
    private final int startColumn;
    private final int rows;

    DisplayOperation(Display display, int startRow, int startColumn, int rows) {
        this.display = display;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.rows = rows;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory) {
        var memoryAddress = registers.index().value();
        for (var row = startRow; row < startRow + rows; row++, memoryAddress = memoryAddress.next()) {
            var bits = randomAccessMemory.value(memoryAddress);
            for (int column = startColumn, bitIndex = 0; bitIndex < 8; column++, bitIndex++) {
                if (bits.has(bitIndex)) {
                    display.flipPixel(row, column);
                }
            }
        }
    }
}

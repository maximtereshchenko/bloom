package com.github.maximtereshchenko.bloom.domain;

/**
 * Dxyn - DRW Vx, Vy, nibble. Display n-byte sprite starting at memory location I at (Vx, Vy),
 * set VF = collision. The interpreter reads n bytes from memory, starting at the address stored
 * in I. These bytes are then displayed as sprites on screen at coordinates (Vx, Vy). Sprites are
 * XORed onto the existing screen. If this causes any pixels to be erased, VF is set to 1,
 * otherwise it is set to 0. The starting position of the sprite will wrap. In other words, an X
 * coordinate of 5 is the same as an X of 68 (since the screen is 64 pixels wide). However, the
 * actual drawing of the sprite should not wrap. If a sprite is drawn near the edge of the
 * screen, it should be clipped, and not wrap.
 */
final class DisplayOperation implements Operation {

    private final Registers registers;
    private final RandomAccessMemory randomAccessMemory;
    private final StagingDisplay display;
    private final HexadecimalSymbol startRowRegister;
    private final HexadecimalSymbol startColumnRegister;
    private final UnsignedByte rows;

    DisplayOperation(
        Registers registers,
        RandomAccessMemory randomAccessMemory,
        StagingDisplay display,
        HexadecimalSymbol startRowRegister,
        HexadecimalSymbol startColumnRegister,
        UnsignedByte rows
    ) {
        this.registers = registers;
        this.randomAccessMemory = randomAccessMemory;
        this.display = display;
        this.startRowRegister = startRowRegister;
        this.startColumnRegister = startColumnRegister;
        this.rows = rows;
    }

    @Override
    public void execute() {
        draw(
            registers.generalPurpose(startRowRegister).get().moduloRemainder(StagingDisplay.HEIGHT),
            registers.generalPurpose(startColumnRegister).get().moduloRemainder(StagingDisplay.WIDTH)
        );
    }

    private void draw(UnsignedByte startRow, UnsignedByte startColumn) {
        registers.flagRegister().set(false);
        var currentMemoryAddress = registers.index().get();
        update(startRow, startColumn, currentMemoryAddress);
        display.draw();
    }

    private void update(
        UnsignedByte startRow,
        UnsignedByte startColumn,
        MemoryAddress currentMemoryAddress
    ) {
        for (var row : startRow.rangeTo(endRow(startRow))) {
            updateRow(row, startColumn, randomAccessMemory.get(currentMemoryAddress));
            currentMemoryAddress = currentMemoryAddress.next();
        }
    }

    private void updateRow(UnsignedByte row, UnsignedByte startColumn, UnsignedByte value) {
        var columnIterator = startColumn.rangeTo(StagingDisplay.WIDTH).iterator();
        var bitIterator = value.bits().iterator();
        while (columnIterator.hasNext() && bitIterator.hasNext()) {
            var column = columnIterator.next();
            if (Boolean.TRUE.equals(bitIterator.next())) {
                display.flipPixel(row, column);
                if (!display.isPixelEnabled(row, column)) {
                    registers.flagRegister().set(true);
                }
            }
        }
    }

    private UnsignedByte endRow(UnsignedByte startRow) {
        var requested = startRow.sum(rows);
        if (requested.compareTo(StagingDisplay.HEIGHT) < 0) {
            return requested;
        }
        return StagingDisplay.HEIGHT;
    }
}

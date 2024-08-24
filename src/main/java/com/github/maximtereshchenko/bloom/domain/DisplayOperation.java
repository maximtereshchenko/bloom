package com.github.maximtereshchenko.bloom.domain;

/**
 * Dxyn - DRW Vx, Vy, nibble. Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision. The
 * interpreter reads n bytes from memory, starting at the address stored in I. These bytes are then displayed as sprites
 * on screen at coordinates (Vx, Vy). Sprites are XORed onto the existing screen. If this causes any pixels to be
 * erased, VF is set to 1, otherwise it is set to 0. The starting position of the sprite will wrap. In other words, an X
 * coordinate of 5 is the same as an X of 68 (since the screen is 64 pixels wide). However, the actual drawing of the
 * sprite should not wrap. If a sprite is drawn near the edge of the screen, it should be clipped, and not wrap.
 */
final class DisplayOperation implements Operation {

    private final HexadecimalSymbol startRowRegister;
    private final HexadecimalSymbol startColumnRegister;
    private final UnsignedByte rows;

    DisplayOperation(HexadecimalSymbol startRowRegister, HexadecimalSymbol startColumnRegister, UnsignedByte rows) {
        this.startRowRegister = startRowRegister;
        this.startColumnRegister = startColumnRegister;
        this.rows = rows;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        display(
            randomAccessMemory,
            registers.flagRegister(),
            display,
            registers.index().get(),
            registers.generalPurpose(startRowRegister).get().moduloRemainder(Display.HEIGHT),
            registers.generalPurpose(startColumnRegister).get().moduloRemainder(Display.WIDTH)
        );
    }

    private void display(
        RandomAccessMemory randomAccessMemory,
        FlagRegister flagRegister,
        Display display,
        MemoryAddress startMemoryAddress,
        UnsignedByte startRow,
        UnsignedByte startColumn
    ) {
        flagRegister.set(false);
        var currentMemoryAddress = startMemoryAddress;
        for (var row : startRow.rangeTo(endRow(startRow))) {
            displayRow(flagRegister, display, row, startColumn, randomAccessMemory.get(currentMemoryAddress));
            currentMemoryAddress = currentMemoryAddress.next();
        }
    }

    private void displayRow(
        FlagRegister flagRegister,
        Display display,
        UnsignedByte row,
        UnsignedByte startColumn,
        UnsignedByte value
    ) {
        var columnIterator = startColumn.rangeTo(Display.WIDTH).iterator();
        var bitIterator = value.bits().iterator();
        while (columnIterator.hasNext() && bitIterator.hasNext()) {
            var column = columnIterator.next();
            if (Boolean.TRUE.equals(bitIterator.next())) {
                display.flipPixel(row, column);
                if (!display.isPixelEnabled(row, column)) {
                    flagRegister.set(true);
                }
            }
        }
    }

    private UnsignedByte endRow(UnsignedByte startRow) {
        var requested = startRow.sum(rows);
        if (requested.compareTo(Display.HEIGHT) < 0) {
            return requested;
        }
        return Display.HEIGHT;
    }
}

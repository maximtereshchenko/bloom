package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy0 - LD Vx, Vy. Set Vx = Vy. Stores the value of register Vy in register Vx.
 */
final class CopyOperation implements Operation {

    private final HexadecimalSymbol from;
    private final HexadecimalSymbol to;

    CopyOperation(HexadecimalSymbol from, HexadecimalSymbol hexadecimalSymbol) {
        this.from = from;
        to = hexadecimalSymbol;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.generalPurpose(to).set(registers.generalPurpose(from).get());
    }
}

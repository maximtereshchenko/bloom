package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy0 - LD Vx, Vy. Set Vx = Vy. Stores the value of register Vy in register Vx.
 */
final class CopyOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol from;
    private final HexadecimalSymbol to;

    CopyOperation(Registers registers, HexadecimalSymbol from, HexadecimalSymbol to) {
        this.registers = registers;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute() {
        registers.generalPurpose(to).set(registers.generalPurpose(from).get());
    }
}

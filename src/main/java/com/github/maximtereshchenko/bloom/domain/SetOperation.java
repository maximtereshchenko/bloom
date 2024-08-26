package com.github.maximtereshchenko.bloom.domain;

/**
 * 6xkk - LD Vx, byte. Set Vx = kk. The interpreter puts the value kk into register Vx.
 */
final class SetOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol registerName;
    private final UnsignedByte value;

    SetOperation(Registers registers, HexadecimalSymbol registerName, UnsignedByte value) {
        this.registers = registers;
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute() {
        registers.generalPurpose(registerName).set(value);
    }
}

package com.github.maximtereshchenko.bloom.domain;

abstract class SkipBasedOnSingleValueOperation extends SkipConditionallyOperation {

    private final Registers registers;
    private final HexadecimalSymbol registerName;
    private final UnsignedByte value;

    SkipBasedOnSingleValueOperation(Registers registers, HexadecimalSymbol registerName, UnsignedByte value) {
        super(registers);
        this.registers = registers;
        this.registerName = registerName;
        this.value = value;
    }

    boolean registerValueEquals() {
        return registers.generalPurpose(registerName).get().equals(value);
    }
}

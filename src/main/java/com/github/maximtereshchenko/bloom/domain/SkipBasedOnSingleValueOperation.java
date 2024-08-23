package com.github.maximtereshchenko.bloom.domain;

abstract class SkipBasedOnSingleValueOperation extends SkipConditionallyOperation {

    private final HexadecimalSymbol registerName;
    private final UnsignedByte value;

    SkipBasedOnSingleValueOperation(HexadecimalSymbol registerName, UnsignedByte value) {
        this.registerName = registerName;
        this.value = value;
    }

    boolean registerValueEquals(Registers registers) {
        return registers.generalPurpose(registerName).get().equals(value);
    }
}

package com.github.maximtereshchenko.bloom.domain;

abstract class SkipBasedOnSingleRegisterValueOperation extends SkipConditionallyOperation {

    private final HexadecimalSymbol registerName;
    private final Byte value;

    SkipBasedOnSingleRegisterValueOperation(HexadecimalSymbol registerName, Byte value) {
        this.registerName = registerName;
        this.value = value;
    }

    boolean registerValueEquals(Registers registers) {
        return registers.generalPurpose(registerName).get().equals(value);
    }
}

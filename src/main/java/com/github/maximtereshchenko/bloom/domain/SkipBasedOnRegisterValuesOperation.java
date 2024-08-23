package com.github.maximtereshchenko.bloom.domain;

abstract class SkipBasedOnRegisterValuesOperation extends SkipConditionallyOperation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    SkipBasedOnRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    boolean registerValuesEqual(Registers registers) {
        return registers.generalPurpose(firstRegisterName).get()
            .equals(registers.generalPurpose(secondRegisterName).get());
    }
}

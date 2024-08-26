package com.github.maximtereshchenko.bloom.domain;

abstract class SkipBasedOnMultipleValuesOperation extends SkipConditionallyOperation {

    private final Registers registers;
    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    SkipBasedOnMultipleValuesOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        super(registers);
        this.registers = registers;
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    boolean registerValuesEqual() {
        return registers.generalPurpose(firstRegisterName).get()
            .equals(registers.generalPurpose(secondRegisterName).get());
    }
}

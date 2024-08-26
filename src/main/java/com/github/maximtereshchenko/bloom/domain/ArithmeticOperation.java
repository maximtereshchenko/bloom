package com.github.maximtereshchenko.bloom.domain;

abstract class ArithmeticOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    ArithmeticOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        this.registers = registers;
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute() {
        var register = registers.generalPurpose(firstRegisterName);
        var first = register.get();
        var second = registers.generalPurpose(secondRegisterName).get();
        register.set(result(first, second));
        registers.flagRegister().set(shouldEnableFlagRegister(first, second));
    }

    abstract boolean shouldEnableFlagRegister(UnsignedByte first, UnsignedByte second);

    abstract UnsignedByte result(UnsignedByte first, UnsignedByte second);
}

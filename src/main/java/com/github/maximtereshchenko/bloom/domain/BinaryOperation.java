package com.github.maximtereshchenko.bloom.domain;

abstract class BinaryOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    BinaryOperation(Registers registers, HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.registers = registers;
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute() {
        var register = registers.generalPurpose(firstRegisterName);
        register.set(operation(register.get(), registers.generalPurpose(secondRegisterName).get()));
        registers.flagRegister().set(false);
    }

    abstract UnsignedByte operation(UnsignedByte first, UnsignedByte second);
}

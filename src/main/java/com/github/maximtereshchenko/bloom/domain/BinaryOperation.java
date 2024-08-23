package com.github.maximtereshchenko.bloom.domain;

abstract class BinaryOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    BinaryOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(firstRegisterName);
        register.set(operation(register.get(), registers.generalPurpose(secondRegisterName).get()));
    }

    abstract UnsignedByte operation(UnsignedByte first, UnsignedByte second);
}

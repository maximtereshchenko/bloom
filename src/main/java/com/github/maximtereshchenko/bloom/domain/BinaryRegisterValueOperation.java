package com.github.maximtereshchenko.bloom.domain;

abstract class BinaryRegisterValueOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    BinaryRegisterValueOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(firstRegisterName);
        register.set(operation(register.get(), registers.generalPurpose(secondRegisterName).get()));
    }

    abstract Byte operation(Byte first, Byte second);
}

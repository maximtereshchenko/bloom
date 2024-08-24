package com.github.maximtereshchenko.bloom.domain;

abstract class ArithmeticOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    ArithmeticOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(firstRegisterName);
        var first = register.get();
        var second = registers.generalPurpose(secondRegisterName).get();
        register.set(result(first, second));
        registers.flagRegister().set(shouldEnableFlagRegister(first, second));
    }

    abstract boolean shouldEnableFlagRegister(UnsignedByte first, UnsignedByte second);

    abstract UnsignedByte result(UnsignedByte first, UnsignedByte second);
}

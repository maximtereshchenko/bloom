package com.github.maximtereshchenko.bloom.domain;

abstract class ArithmeticRegisterValuesOperation implements Operation {

    private final HexadecimalSymbol firstRegisterName;
    private final HexadecimalSymbol secondRegisterName;

    ArithmeticRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var flagRegister = registers.flagRegister();
        flagRegister.disable();
        var register = registers.generalPurpose(firstRegisterName);
        var first = register.get();
        var second = registers.generalPurpose(secondRegisterName).get();
        if (shouldEnableFlagRegister(first, second)) {
            flagRegister.enable();
        }
        register.set(result(first, second));
    }

    abstract boolean shouldEnableFlagRegister(Byte first, Byte second);

    abstract Byte result(Byte first, Byte second);
}

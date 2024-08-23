package com.github.maximtereshchenko.bloom.domain;

/**
 * 7xkk - ADD Vx, byte. Set Vx = Vx + kk. Adds the value kk to the value of register Vx, then stores the result in Vx.
 */
final class AddToRegisterValueOperation implements Operation {

    private final HexadecimalSymbol registerName;
    private final UnsignedByte value;

    AddToRegisterValueOperation(HexadecimalSymbol registerName, UnsignedByte value) {
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(registerName);
        register.set(register.get().sum(value));
    }
}

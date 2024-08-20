package com.github.maximtereshchenko.bloom.domain;

/**
 * Set the register VX to the value NN.
 */
final class SetRegisterValueOperation implements Operation {

    private final HexadecimalSymbol registerName;
    private final Byte value;

    SetRegisterValueOperation(HexadecimalSymbol registerName, Byte value) {
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Display display) {
        registers.generalPurpose(registerName).set(value);
    }
}

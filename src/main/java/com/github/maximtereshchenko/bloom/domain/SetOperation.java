package com.github.maximtereshchenko.bloom.domain;

/**
 * 6xkk - LD Vx, byte. Set Vx = kk. The interpreter puts the value kk into register Vx.
 */
final class SetOperation implements Operation {

    private final HexadecimalSymbol registerName;
    private final UnsignedByte value;

    SetOperation(HexadecimalSymbol registerName, UnsignedByte value) {
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.generalPurpose(registerName).set(value);
    }
}

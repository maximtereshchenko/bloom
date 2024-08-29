package com.github.maximtereshchenko.bloom.domain;

/**
 * 3xkk - SE Vx, byte. Skip next instruction if Vx = kk. The interpreter compares register Vx to
 * kk, and if they are equal, increments the program counter.
 */
class SkipIfSingleRegisterValueEqualOperation extends SkipBasedOnSingleValueOperation {

    SkipIfSingleRegisterValueEqualOperation(
        Registers registers,
        HexadecimalSymbol registerName,
        UnsignedByte value
    ) {
        super(registers, registerName, value);
    }

    @Override
    boolean shouldSkip() {
        return registerValueEquals();
    }
}

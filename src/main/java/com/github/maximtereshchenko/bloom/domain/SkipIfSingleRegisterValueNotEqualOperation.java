package com.github.maximtereshchenko.bloom.domain;

/**
 * 4xkk - SNE Vx, byte. Skip next instruction if Vx != kk. The interpreter compares register Vx to kk, and if they are
 * not equal, increments the program counter.
 */
class SkipIfSingleRegisterValueNotEqualOperation extends SkipBasedOnSingleValueOperation {

    SkipIfSingleRegisterValueNotEqualOperation(
        Registers registers,
        HexadecimalSymbol registerName,
        UnsignedByte value
    ) {
        super(registers, registerName, value);
    }

    @Override
    boolean shouldSkip() {
        return !registerValueEquals();
    }
}

package com.github.maximtereshchenko.bloom.domain;

/**
 * 4xkk - SNE Vx, byte. Skip next instruction if Vx != kk. The interpreter compares register Vx to kk, and if they are
 * not equal, increments the program counter.
 */
class SkipIfSingleRegisterValueNotEqualOperation extends SkipBasedOnSingleValueOperation {

    SkipIfSingleRegisterValueNotEqualOperation(HexadecimalSymbol registerName, UnsignedByte value) {
        super(registerName, value);
    }

    @Override
    boolean shouldSkip(Registers registers) {
        return !registerValueEquals(registers);
    }
}

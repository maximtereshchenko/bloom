package com.github.maximtereshchenko.bloom.domain;

/**
 * 5xy0 - SE Vx, Vy. Skip next instruction if Vx = Vy. The interpreter compares register Vx to
 * register Vy, and if they are equal, increments the program counter.
 */
class SkipIfRegisterValuesEqualOperation extends SkipBasedOnMultipleValuesOperation {

    SkipIfRegisterValuesEqualOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        super(registers, firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldSkip() {
        return registerValuesEqual();
    }
}

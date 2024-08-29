package com.github.maximtereshchenko.bloom.domain;

/**
 * 9xy0 - SNE Vx, Vy. Skip next instruction if Vx != Vy. The values of Vx and Vy are compared,
 * and if they are not equal, the program counter is increased.
 */
class SkipIfRegisterValuesNotEqualOperation extends SkipBasedOnMultipleValuesOperation {

    SkipIfRegisterValuesNotEqualOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        super(registers, firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldSkip() {
        return !registerValuesEqual();
    }
}

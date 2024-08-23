package com.github.maximtereshchenko.bloom.domain;

/**
 * 9xy0 - SNE Vx, Vy. Skip next instruction if Vx != Vy. The values of Vx and Vy are compared, and if they are not
 * equal, the program counter is increased.
 */
class SkipIfRegisterValuesNotEqualOperation extends SkipBasedOnMultipleValuesOperation {

    SkipIfRegisterValuesNotEqualOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        super(firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldSkip(Registers registers) {
        return !registerValuesEqual(registers);
    }
}

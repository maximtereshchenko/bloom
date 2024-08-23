package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy5 - SUB Vx, Vy. Set Vx = Vx - Vy, set VF = NOT borrow. If Vx > Vy, then VF is set to 1, otherwise 0. Then Vy is
 * subtracted from Vx, and the results stored in Vx.
 */
final class SubtractRegisterValuesOperation extends ArithmeticRegisterValuesOperation {

    SubtractRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        super(firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldEnableFlagRegister(Byte first, Byte second) {
        return first.compareTo(second) > 0;
    }

    @Override
    Byte result(Byte first, Byte second) {
        return first.difference(second);
    }
}

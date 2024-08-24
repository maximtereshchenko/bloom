package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy7 - SUBN Vx, Vy. Set Vx = Vy - Vx, set VF = NOT borrow. If Vy > Vx, then VF is set to 1, otherwise 0. Then Vx is
 * subtracted from Vy, and the results stored in Vx.
 */
final class NegativeSubtractOperation extends ArithmeticOperation {

    NegativeSubtractOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        super(firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldEnableFlagRegister(UnsignedByte first, UnsignedByte second) {
        return second.compareTo(first) >= 0;
    }

    @Override
    UnsignedByte result(UnsignedByte first, UnsignedByte second) {
        return second.difference(first);
    }
}

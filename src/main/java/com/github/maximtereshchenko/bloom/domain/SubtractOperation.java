package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy5 - SUB Vx, Vy. Set Vx = Vx - Vy, set VF = NOT borrow. If Vx > Vy, then VF is set to 1,
 * otherwise 0. Then Vy is subtracted from Vx, and the results stored in Vx.
 */
final class SubtractOperation extends ArithmeticOperation {

    SubtractOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        super(registers, firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldEnableFlagRegister(UnsignedByte first, UnsignedByte second) {
        return first.compareTo(second) >= 0;
    }

    @Override
    UnsignedByte result(UnsignedByte first, UnsignedByte second) {
        return first.difference(second);
    }
}

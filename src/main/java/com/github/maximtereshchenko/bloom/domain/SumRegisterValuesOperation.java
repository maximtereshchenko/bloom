package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy4 - ADD Vx, Vy. Set Vx = Vx + Vy, set VF = carry. The values of Vx and Vy are added together. If the result is
 * greater than 8 bits (i.e., > 255) VF is set to 1, otherwise 0. Only the lowest 8 bits of the result are kept, and
 * stored in Vx.
 */
final class SumRegisterValuesOperation extends ArithmeticRegisterValuesOperation {

    SumRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        super(firstRegisterName, secondRegisterName);
    }

    @Override
    boolean shouldEnableFlagRegister(UnsignedByte first, UnsignedByte second) {
        return UnsignedByte.MAX.difference(first).compareTo(second) < 0;
    }

    @Override
    UnsignedByte result(UnsignedByte first, UnsignedByte second) {
        return first.sum(second);
    }
}

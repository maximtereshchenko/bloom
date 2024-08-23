package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy3 - XOR Vx, Vy. Set Vx = Vx XOR Vy. Performs a bitwise exclusive OR on the values of Vx and Vy, then stores the
 * result in Vx. An exclusive OR compares the corresponding bits from two values, and if the bits are not both the same,
 * then the corresponding bit in the result is set to 1. Otherwise, it is 0.
 */
final class BinaryXorRegisterValuesOperation extends BinaryRegisterValueOperation {

    BinaryXorRegisterValuesOperation(HexadecimalSymbol firstRegisterName, HexadecimalSymbol secondRegisterName) {
        super(firstRegisterName, secondRegisterName);
    }

    @Override
    UnsignedByte operation(UnsignedByte first, UnsignedByte second) {
        return first.xor(second);
    }
}

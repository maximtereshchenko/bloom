package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy2 - AND Vx, Vy. Set Vx = Vx AND Vy. Performs a bitwise AND on the values of Vx and Vy, then
 * stores the result in Vx. A bitwise AND compares the corresponding bits from two values, and if
 * both bits are 1, then the same bit in the result is also 1. Otherwise, it is 0. Flag register
 * is set to 0.
 */
final class BinaryAndOperation extends BinaryOperation {

    BinaryAndOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        super(registers, firstRegisterName, secondRegisterName);
    }

    @Override
    UnsignedByte operation(UnsignedByte first, UnsignedByte second) {
        return first.and(second);
    }
}

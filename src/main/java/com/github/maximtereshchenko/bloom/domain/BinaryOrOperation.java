package com.github.maximtereshchenko.bloom.domain;

/**
 * 8xy1 - OR Vx, Vy. Set Vx = Vx OR Vy. Performs a bitwise OR on the values of Vx and Vy, then
 * stores the result in Vx. A bitwise OR compares the corresponding bits from two values, and if
 * either bit is 1, then the same bit in the result is also 1. Otherwise, it is 0. Flag register
 * is set to 0.
 */
final class BinaryOrOperation extends BinaryOperation {

    BinaryOrOperation(
        Registers registers,
        HexadecimalSymbol firstRegisterName,
        HexadecimalSymbol secondRegisterName
    ) {
        super(registers, firstRegisterName, secondRegisterName);
    }

    @Override
    UnsignedByte operation(UnsignedByte first, UnsignedByte second) {
        return first.or(second);
    }
}

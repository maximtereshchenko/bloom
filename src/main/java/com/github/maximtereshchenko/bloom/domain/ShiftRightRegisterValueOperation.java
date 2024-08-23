package com.github.maximtereshchenko.bloom.domain;

import java.util.List;

/**
 * 8xy6 - SHR Vx {, Vy}. Set Vx = Vx SHR 1. If the least-significant bit of Vx is 1, then VF is set to 1, otherwise 0.
 * Then Vx is divided by 2.
 */
final class ShiftRightRegisterValueOperation extends ShiftRegisterValueOperation {

    ShiftRightRegisterValueOperation(HexadecimalSymbol registerName) {
        super(registerName);
    }

    @Override
    Boolean bit(List<Boolean> bits) {
        return bits.getLast();
    }

    @Override
    UnsignedByte operation(UnsignedByte value) {
        return value.shiftedRight();
    }
}

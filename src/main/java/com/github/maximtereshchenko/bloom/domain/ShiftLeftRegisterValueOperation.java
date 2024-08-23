package com.github.maximtereshchenko.bloom.domain;

import java.util.List;

/**
 * 8xyE - SHL Vx {, Vy}. Set Vx = Vx SHL 1. If the most-significant bit of Vx is 1, then VF is set to 1, otherwise to 0.
 * Then Vx is multiplied by 2.
 */
final class ShiftLeftRegisterValueOperation extends ShiftRegisterValueOperation {

    ShiftLeftRegisterValueOperation(HexadecimalSymbol registerName) {
        super(registerName);
    }

    @Override
    Boolean bit(List<Boolean> bits) {
        return bits.getFirst();
    }

    @Override
    UnsignedByte operation(UnsignedByte value) {
        return value.shiftedLeft();
    }
}

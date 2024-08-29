package com.github.maximtereshchenko.bloom.domain;

import java.util.List;

abstract class ShiftOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol fromRegisterName;
    private final HexadecimalSymbol toRegisterName;

    ShiftOperation(
        Registers registers,
        HexadecimalSymbol fromRegisterName,
        HexadecimalSymbol toRegisterName
    ) {
        this.registers = registers;
        this.fromRegisterName = fromRegisterName;
        this.toRegisterName = toRegisterName;
    }

    @Override
    public void execute() {
        var value = registers.generalPurpose(fromRegisterName).get();
        registers.generalPurpose(toRegisterName).set(operation(value));
        registers.flagRegister().set(Boolean.TRUE.equals(bit(value.bits())));
    }

    abstract Boolean bit(List<Boolean> bits);

    abstract UnsignedByte operation(UnsignedByte value);
}

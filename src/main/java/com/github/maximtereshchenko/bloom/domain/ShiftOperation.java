package com.github.maximtereshchenko.bloom.domain;

import java.util.List;

abstract class ShiftOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol registerName;

    ShiftOperation(Registers registers, HexadecimalSymbol registerName) {
        this.registers = registers;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        var register = registers.generalPurpose(registerName);
        var value = register.get();
        register.set(operation(value));
        registers.flagRegister().set(Boolean.TRUE.equals(bit(value.bits())));
    }

    abstract Boolean bit(List<Boolean> bits);

    abstract UnsignedByte operation(UnsignedByte value);
}

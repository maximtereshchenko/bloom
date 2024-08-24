package com.github.maximtereshchenko.bloom.domain;

import java.util.List;

abstract class ShiftOperation implements Operation {

    private final HexadecimalSymbol registerName;

    ShiftOperation(HexadecimalSymbol registerName) {
        this.registerName = registerName;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var register = registers.generalPurpose(registerName);
        var value = register.get();
        register.set(operation(value));
        registers.flagRegister().set(Boolean.TRUE.equals(bit(value.bits())));
    }

    abstract Boolean bit(List<Boolean> bits);

    abstract UnsignedByte operation(UnsignedByte value);
}

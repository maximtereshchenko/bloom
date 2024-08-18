package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

/**
 * Add the value NN to VX.
 */
final class AddValueToRegisterOperation implements Operation {

    private final HexadecimalSymbol registerName;
    private final Byte value;

    AddValueToRegisterOperation(HexadecimalSymbol registerName, Byte value) {
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Display display) {
        var register = registers.generalPurpose(registerName);
        register.set(register.value().sum(value));
    }
}

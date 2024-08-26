package com.github.maximtereshchenko.bloom.domain;

/**
 * 7xkk - ADD Vx, byte. Set Vx = Vx + kk. Adds the value kk to the value of register Vx, then stores the result in Vx.
 */
final class AddOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol registerName;
    private final UnsignedByte value;

    AddOperation(Registers registers, HexadecimalSymbol registerName, UnsignedByte value) {
        this.registers = registers;
        this.registerName = registerName;
        this.value = value;
    }

    @Override
    public void execute() {
        var register = registers.generalPurpose(registerName);
        register.set(register.get().sum(value));
    }
}

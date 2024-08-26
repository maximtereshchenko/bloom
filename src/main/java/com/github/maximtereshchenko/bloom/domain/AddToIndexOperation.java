package com.github.maximtereshchenko.bloom.domain;

/**
 * Fx1E - ADD I, Vx. Set I = I + Vx. The values of I and Vx are added, and the results are stored in I.
 */
final class AddToIndexOperation implements Operation {

    private final Registers registers;
    private final HexadecimalSymbol registerName;

    AddToIndexOperation(Registers registers, HexadecimalSymbol registerName) {
        this.registers = registers;
        this.registerName = registerName;
    }

    @Override
    public void execute() {
        var index = registers.index();
        index.set(index.get().withOffset(registers.generalPurpose(registerName).get()));
    }
}

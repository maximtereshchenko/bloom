package com.github.maximtereshchenko.bloom.domain;

/**
 * Annn - LD I, addr. Set I = nnn. The value of register I is set to nnn.
 */
final class SetIndexOperation implements Operation {

    private final Registers registers;
    private final MemoryAddress memoryAddress;

    SetIndexOperation(Registers registers, MemoryAddress memoryAddress) {
        this.registers = registers;
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute() {
        registers.index().set(memoryAddress);
    }
}

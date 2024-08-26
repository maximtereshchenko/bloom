package com.github.maximtereshchenko.bloom.domain;

/**
 * 1nnn - JP addr. Jump to location nnn. The interpreter sets the program counter to nnn.
 */
final class JumpOperation implements Operation {

    private final Registers registers;
    private final MemoryAddress memoryAddress;

    JumpOperation(Registers registers, MemoryAddress memoryAddress) {
        this.registers = registers;
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute() {
        registers.programCounter().set(memoryAddress);
    }
}

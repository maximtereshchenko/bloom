package com.github.maximtereshchenko.bloom.domain;

/**
 * 1nnn - JP addr. Jump to location nnn. The interpreter sets the program counter to nnn.
 */
final class JumpOperation implements Operation {

    private final MemoryAddress memoryAddress;

    JumpOperation(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.programCounter().set(memoryAddress);
    }
}

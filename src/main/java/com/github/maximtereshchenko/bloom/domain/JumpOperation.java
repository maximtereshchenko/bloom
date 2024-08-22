package com.github.maximtereshchenko.bloom.domain;

/**
 * This instruction should simply set PC to NNN, causing the program to jump to that memory location.
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

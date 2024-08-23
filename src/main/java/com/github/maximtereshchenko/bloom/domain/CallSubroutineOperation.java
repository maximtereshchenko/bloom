package com.github.maximtereshchenko.bloom.domain;

/**
 * 2nnn - CALL addr. Call subroutine at nnn. The interpreter increments the stack pointer, then puts the current PC on
 * the top of the stack. The PC is then set to nnn.
 */
final class CallSubroutineOperation implements Operation {

    private final MemoryAddress memoryAddress;

    CallSubroutineOperation(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var programCounter = registers.programCounter();
        stack.push(programCounter.get());
        programCounter.set(memoryAddress);
    }
}

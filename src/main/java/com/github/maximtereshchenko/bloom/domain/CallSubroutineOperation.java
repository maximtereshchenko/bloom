package com.github.maximtereshchenko.bloom.domain;

/**
 * Calls the subroutine at memory location NNN. This instruction should first push the current PC to the stack, then set
 * PC to NNN.
 */
final class CallSubroutineOperation implements Operation {

    private final MemoryAddress memoryAddress;

    CallSubroutineOperation(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        var programCounter = registers.programCounter();
        stack.push(programCounter.value());
        programCounter.set(memoryAddress);
    }
}

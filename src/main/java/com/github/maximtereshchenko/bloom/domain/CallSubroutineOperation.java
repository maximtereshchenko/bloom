package com.github.maximtereshchenko.bloom.domain;

/**
 * 2nnn - CALL addr. Call subroutine at nnn. The interpreter increments the stack pointer, then puts the current PC on
 * the top of the stack. The PC is then set to nnn.
 */
final class CallSubroutineOperation implements Operation {

    private final Registers registers;
    private final Stack stack;
    private final MemoryAddress memoryAddress;

    CallSubroutineOperation(Registers registers, Stack stack, MemoryAddress memoryAddress) {
        this.registers = registers;
        this.stack = stack;
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute() {
        var programCounter = registers.programCounter();
        stack.push(programCounter.get());
        programCounter.set(memoryAddress);
    }
}

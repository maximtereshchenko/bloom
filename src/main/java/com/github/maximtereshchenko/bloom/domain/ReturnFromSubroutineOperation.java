package com.github.maximtereshchenko.bloom.domain;

/**
 * 00EE - RET. Return from a subroutine. The interpreter sets the program counter to the address at the top of the
 * stack, then subtracts 1 from the stack pointer.
 */
final class ReturnFromSubroutineOperation implements Operation {

    private final Registers registers;
    private final Stack stack;

    ReturnFromSubroutineOperation(Registers registers, Stack stack) {
        this.registers = registers;
        this.stack = stack;
    }

    @Override
    public void execute() {
        registers.programCounter().set(stack.pick());
        stack.pop();
    }
}

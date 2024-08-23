package com.github.maximtereshchenko.bloom.domain;

/**
 * 00EE - RET. Return from a subroutine. The interpreter sets the program counter to the address at the top of the
 * stack, then subtracts 1 from the stack pointer.
 */
final class ReturnFromSubroutineOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.programCounter().set(stack.pick());
        stack.pop();
    }
}

package com.github.maximtereshchenko.bloom.domain;

/**
 * Remove (“pop”) the last address from the stack and set the PC to it.
 */
final class ReturnFromSubroutineOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.programCounter().set(stack.pick());
        stack.pop();
    }
}

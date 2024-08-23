package com.github.maximtereshchenko.bloom.domain;

abstract class SkipConditionallyOperation implements Operation {

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        if (shouldSkip(registers)) {
            var programCounter = registers.programCounter();
            programCounter.set(programCounter.get().next().next());
        }
    }

    abstract boolean shouldSkip(Registers registers);
}

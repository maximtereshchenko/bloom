package com.github.maximtereshchenko.bloom.domain;

abstract class SkipConditionallyOperation implements Operation {

    private final Registers registers;

    SkipConditionallyOperation(Registers registers) {
        this.registers = registers;
    }

    @Override
    public void execute() {
        if (shouldSkip()) {
            var programCounter = registers.programCounter();
            programCounter.set(programCounter.get().next().next());
        }
    }

    abstract boolean shouldSkip();
}

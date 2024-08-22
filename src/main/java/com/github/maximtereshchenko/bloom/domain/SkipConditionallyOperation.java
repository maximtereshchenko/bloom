package com.github.maximtereshchenko.bloom.domain;

import java.util.function.Predicate;

/**
 * 3XNN will skip one instruction if the value in VX is equal to NN, and 4XNN will skip if they are not equal. 5XY0
 * skips if the values in VX and VY are equal, while 9XY0 skips if they are not equal.
 */
final class SkipConditionallyOperation implements Operation {

    private final Predicate<Registers> predicate;

    SkipConditionallyOperation(Predicate<Registers> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        if (predicate.test(registers)) {
            var programCounter = registers.programCounter();
            programCounter.set(programCounter.get().next().next());
        }
    }
}

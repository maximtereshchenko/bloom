package com.github.maximtereshchenko.bloom.domain;

import com.github.maximtereshchenko.bloom.api.Display;

/**
 * Set the index register I to the value NNN.
 */
final class SetIndexRegisterValueOperation implements Operation {

    private final MemoryAddress memoryAddress;

    SetIndexRegisterValueOperation(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Display display) {
        registers.index().set(memoryAddress);
    }
}

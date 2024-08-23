package com.github.maximtereshchenko.bloom.domain;

/**
 * Annn - LD I, addr. Set I = nnn. The value of register I is set to nnn.
 */
final class SetIndexRegisterValueOperation implements Operation {

    private final MemoryAddress memoryAddress;

    SetIndexRegisterValueOperation(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public void execute(Registers registers, RandomAccessMemory randomAccessMemory, Stack stack, Display display) {
        registers.index().set(memoryAddress);
    }
}

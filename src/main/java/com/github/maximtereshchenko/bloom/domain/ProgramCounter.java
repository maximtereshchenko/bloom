package com.github.maximtereshchenko.bloom.domain;

final class ProgramCounter implements Register<MemoryAddress> {

    static final MemoryAddress INITIAL = MemoryAddress.from(0x200);

    private MemoryAddress memoryAddress = INITIAL;

    @Override
    public MemoryAddress value() {
        return memoryAddress;
    }

    @Override
    public void set(MemoryAddress value) {
        memoryAddress = value;
    }
}

package com.github.maximtereshchenko.bloom.domain;

final class Index implements Register<MemoryAddress> {

    private MemoryAddress memoryAddress = MemoryAddress.from(0);

    @Override
    public MemoryAddress get() {
        return memoryAddress;
    }

    @Override
    public void set(MemoryAddress value) {
        memoryAddress = value;
    }
}

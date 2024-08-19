package com.github.maximtereshchenko.bloom.application;

final class Jump {

    private final MemoryAddress memoryAddress;

    Jump(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return "1" + memoryAddress;
    }
}

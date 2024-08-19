package com.github.maximtereshchenko.bloom.application;

final class SetIndexRegisterValue {

    private final MemoryAddress memoryAddress;

    SetIndexRegisterValue(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return "A" + memoryAddress;
    }
}

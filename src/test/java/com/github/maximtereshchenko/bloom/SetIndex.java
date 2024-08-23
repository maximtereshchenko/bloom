package com.github.maximtereshchenko.bloom;

final class SetIndex {

    private final MemoryAddress memoryAddress;

    SetIndex(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return "A" + memoryAddress;
    }
}

package com.github.maximtereshchenko.bloom;

final class JumpWithOffset {

    private final MemoryAddress memoryAddress;

    JumpWithOffset(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return "B" + memoryAddress;
    }
}

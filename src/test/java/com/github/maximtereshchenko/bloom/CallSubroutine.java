package com.github.maximtereshchenko.bloom;

final class CallSubroutine {

    private final MemoryAddress memoryAddress;

    CallSubroutine(MemoryAddress memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return "2" + memoryAddress;
    }
}

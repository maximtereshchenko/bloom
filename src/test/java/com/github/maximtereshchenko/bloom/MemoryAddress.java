package com.github.maximtereshchenko.bloom;

final class MemoryAddress {

    private final int index;

    MemoryAddress(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "%X".formatted(0x200 + index * 2);
    }
}

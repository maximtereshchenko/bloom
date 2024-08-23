package com.github.maximtereshchenko.bloom;

final class ShiftRight {

    private final char registerName;

    ShiftRight(char registerName) {
        this.registerName = registerName;
    }

    @Override
    public String toString() {
        return "8%c06".formatted(registerName);
    }
}

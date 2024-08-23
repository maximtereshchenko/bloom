package com.github.maximtereshchenko.bloom;

final class ShiftLeft {

    private final char registerName;

    ShiftLeft(char registerName) {
        this.registerName = registerName;
    }

    @Override
    public String toString() {
        return "8%c0E".formatted(registerName);
    }
}

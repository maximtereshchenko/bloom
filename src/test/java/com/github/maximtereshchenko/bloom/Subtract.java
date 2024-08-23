package com.github.maximtereshchenko.bloom;

final class Subtract {

    private final char firstRegister;
    private final char secondRegister;

    Subtract(char firstRegister, char secondRegister) {
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public String toString() {
        return "8%c%c5".formatted(firstRegister, secondRegister);
    }
}

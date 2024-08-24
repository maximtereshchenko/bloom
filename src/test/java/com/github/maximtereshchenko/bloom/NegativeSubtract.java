package com.github.maximtereshchenko.bloom;

final class NegativeSubtract {

    private final char firstRegister;
    private final char secondRegister;

    NegativeSubtract(char firstRegister, char secondRegister) {
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public String toString() {
        return "8%c%c7".formatted(firstRegister, secondRegister);
    }
}

package com.github.maximtereshchenko.bloom;

final class Sum {

    private final char firstRegister;
    private final char secondRegister;

    Sum(char firstRegister, char secondRegister) {
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public String toString() {
        return "8%c%c4".formatted(firstRegister, secondRegister);
    }
}

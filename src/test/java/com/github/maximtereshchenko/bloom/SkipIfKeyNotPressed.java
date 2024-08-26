package com.github.maximtereshchenko.bloom;

final class SkipIfKeyNotPressed {

    private final char register;

    SkipIfKeyNotPressed(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "E%cA1".formatted(register);
    }
}

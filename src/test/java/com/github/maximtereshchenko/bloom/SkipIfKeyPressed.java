package com.github.maximtereshchenko.bloom;

final class SkipIfKeyPressed {

    private final char register;

    SkipIfKeyPressed(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "E%c9E".formatted(register);
    }
}

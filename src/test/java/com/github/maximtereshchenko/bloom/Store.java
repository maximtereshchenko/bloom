package com.github.maximtereshchenko.bloom;

final class Store {

    private final char register;

    Store(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c55".formatted(register);
    }
}

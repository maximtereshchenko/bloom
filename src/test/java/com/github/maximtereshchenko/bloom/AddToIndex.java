package com.github.maximtereshchenko.bloom;

final class AddToIndex {

    private final char register;

    AddToIndex(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c1E".formatted(register);
    }
}

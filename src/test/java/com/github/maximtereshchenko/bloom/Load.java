package com.github.maximtereshchenko.bloom;

final class Load {

    private final char register;

    Load(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c65".formatted(register);
    }
}

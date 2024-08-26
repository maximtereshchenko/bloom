package com.github.maximtereshchenko.bloom;

final class GetKey {

    private final char register;

    GetKey(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c0A".formatted(register);
    }
}

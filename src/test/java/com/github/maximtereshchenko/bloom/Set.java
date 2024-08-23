package com.github.maximtereshchenko.bloom;

final class Set {

    private final char register;
    private final String value;

    Set(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "6" + register + value;
    }
}

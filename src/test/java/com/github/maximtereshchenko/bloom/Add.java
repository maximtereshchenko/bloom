package com.github.maximtereshchenko.bloom;

final class Add {

    private final char register;
    private final String value;

    Add(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "7" + register + value;
    }
}

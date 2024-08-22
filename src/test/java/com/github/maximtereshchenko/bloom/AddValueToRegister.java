package com.github.maximtereshchenko.bloom;

final class AddValueToRegister {

    private final char register;
    private final String value;

    AddValueToRegister(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "7" + register + value;
    }
}

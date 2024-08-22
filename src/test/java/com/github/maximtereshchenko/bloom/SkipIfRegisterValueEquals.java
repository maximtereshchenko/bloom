package com.github.maximtereshchenko.bloom;

final class SkipIfRegisterValueEquals {

    private final char register;
    private final String value;

    SkipIfRegisterValueEquals(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "3" + register + value;
    }
}

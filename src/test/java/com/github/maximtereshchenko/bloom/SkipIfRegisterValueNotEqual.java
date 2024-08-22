package com.github.maximtereshchenko.bloom;

final class SkipIfRegisterValueNotEqual {

    private final char register;
    private final String value;

    SkipIfRegisterValueNotEqual(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "4" + register + value;
    }
}

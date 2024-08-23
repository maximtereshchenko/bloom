package com.github.maximtereshchenko.bloom;

final class SkipIfRegisterValueEqual {

    private final char register;
    private final String value;

    SkipIfRegisterValueEqual(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "3" + register + value;
    }
}

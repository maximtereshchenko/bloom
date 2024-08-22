package com.github.maximtereshchenko.bloom;

final class SetRegisterValue {

    private final char register;
    private final String value;

    SetRegisterValue(char register, String value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public String toString() {
        return "6" + register + value;
    }
}

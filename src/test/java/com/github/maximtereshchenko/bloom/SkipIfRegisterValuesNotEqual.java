package com.github.maximtereshchenko.bloom;

final class SkipIfRegisterValuesNotEqual {

    private final char firstRegister;
    private final char secondRegister;

    SkipIfRegisterValuesNotEqual(char firstRegister, char secondRegister) {
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public String toString() {
        return "9%c%c0".formatted(firstRegister, secondRegister);
    }
}

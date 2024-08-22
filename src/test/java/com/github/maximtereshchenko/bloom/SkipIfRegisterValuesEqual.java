package com.github.maximtereshchenko.bloom;

final class SkipIfRegisterValuesEqual {

    private final char firstRegister;
    private final char secondRegister;

    SkipIfRegisterValuesEqual(char firstRegister, char secondRegister) {
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public String toString() {
        return "5%c%c0".formatted(firstRegister, secondRegister);
    }
}

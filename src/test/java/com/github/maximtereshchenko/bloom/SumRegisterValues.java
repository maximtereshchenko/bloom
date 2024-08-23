package com.github.maximtereshchenko.bloom;

final class SumRegisterValues {

    private final char firstRegister;
    private final char secondRegister;

    SumRegisterValues(char firstRegister, char secondRegister) {
        this.firstRegister = firstRegister;
        this.secondRegister = secondRegister;
    }

    @Override
    public String toString() {
        return "8%c%c4".formatted(firstRegister, secondRegister);
    }
}

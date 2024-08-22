package com.github.maximtereshchenko.bloom;

final class BinaryAnd {

    private final char firstRegisterName;
    private final char secondRegisterName;

    BinaryAnd(char firstRegisterName, char secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public String toString() {
        return "8%c%c2".formatted(firstRegisterName, secondRegisterName);
    }
}

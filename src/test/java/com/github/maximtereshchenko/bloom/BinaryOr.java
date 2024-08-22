package com.github.maximtereshchenko.bloom;

final class BinaryOr {

    private final char firstRegisterName;
    private final char secondRegisterName;

    BinaryOr(char firstRegisterName, char secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public String toString() {
        return "8%c%c1".formatted(firstRegisterName, secondRegisterName);
    }
}

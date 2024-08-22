package com.github.maximtereshchenko.bloom;

final class BinaryXor {

    private final char firstRegisterName;
    private final char secondRegisterName;

    BinaryXor(char firstRegisterName, char secondRegisterName) {
        this.firstRegisterName = firstRegisterName;
        this.secondRegisterName = secondRegisterName;
    }

    @Override
    public String toString() {
        return "8%c%c3".formatted(firstRegisterName, secondRegisterName);
    }
}

package com.github.maximtereshchenko.bloom;

final class Copy {

    private final char fromRegister;
    private final char toRegister;

    Copy(char fromRegister, char toRegister) {
        this.fromRegister = fromRegister;
        this.toRegister = toRegister;
    }

    @Override
    public String toString() {
        return "8%c%c0".formatted(toRegister, fromRegister);
    }
}

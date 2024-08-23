package com.github.maximtereshchenko.bloom;

final class ConvertToBinaryCodedDecimal {

    private final char register;

    ConvertToBinaryCodedDecimal(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c33".formatted(register);
    }
}

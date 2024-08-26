package com.github.maximtereshchenko.bloom;

final class ReadDelayTimer {

    private final char register;

    ReadDelayTimer(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c07".formatted(register);
    }
}

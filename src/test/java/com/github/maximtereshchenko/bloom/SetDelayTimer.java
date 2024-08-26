package com.github.maximtereshchenko.bloom;

final class SetDelayTimer {

    private final char register;

    SetDelayTimer(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c15".formatted(register);
    }
}

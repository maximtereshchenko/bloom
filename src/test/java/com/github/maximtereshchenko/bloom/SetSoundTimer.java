package com.github.maximtereshchenko.bloom;

final class SetSoundTimer {

    private final char register;

    SetSoundTimer(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c18".formatted(register);
    }
}

package com.github.maximtereshchenko.bloom;

final class SetFontCharacter {

    private final char register;

    SetFontCharacter(char register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "F%c29".formatted(register);
    }
}

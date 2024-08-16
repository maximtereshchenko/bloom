package com.github.maximtereshchenko.bloom;

record SetFontCharacter(char character) implements Instruction {

    @Override
    public String hexadecimal() {
        return "F%c29".formatted(character);
    }
}

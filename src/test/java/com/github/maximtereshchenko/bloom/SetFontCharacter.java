package com.github.maximtereshchenko.bloom;

record SetFontCharacter(char register) implements Instruction {

    @Override
    public String hexadecimal() {
        return "F%c29".formatted(register);
    }
}

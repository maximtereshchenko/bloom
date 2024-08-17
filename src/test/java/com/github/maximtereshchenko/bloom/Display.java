package com.github.maximtereshchenko.bloom;

record Display(char startRowRegister, char startColumnRegister, int rows) implements Instruction {

    @Override
    public String hexadecimal() {
        return "D" + startRowRegister + startColumnRegister + rows;
    }
}

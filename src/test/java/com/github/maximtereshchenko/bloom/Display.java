package com.github.maximtereshchenko.bloom;

record Display(int startRow, int startColumn, int rows) implements Instruction {

    @Override
    public String hexadecimal() {
        return "D" + startRow + startColumn + rows;
    }
}

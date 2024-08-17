package com.github.maximtereshchenko.bloom;

record SetRegisterValue(char register, String value) implements Instruction {

    @Override
    public String hexadecimal() {
        return "6" + register + value;
    }
}

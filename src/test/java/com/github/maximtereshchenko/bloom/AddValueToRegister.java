package com.github.maximtereshchenko.bloom;

record AddValueToRegister(char register, String value) implements Instruction {

    @Override
    public String hexadecimal() {
        return "7" + register + value;
    }
}

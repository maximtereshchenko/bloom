package com.github.maximtereshchenko.bloom;

record Jump(int index) implements Instruction {

    @Override
    public String hexadecimal() {
        return "1%X".formatted(0x200 + index * 2);
    }
}

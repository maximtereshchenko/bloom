package com.github.maximtereshchenko.bloom;

final class Random {

    private final char register;
    private final String mask;

    Random(char register, String mask) {
        this.register = register;
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "C" + register + mask;
    }
}

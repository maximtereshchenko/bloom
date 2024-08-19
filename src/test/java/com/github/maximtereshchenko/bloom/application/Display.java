package com.github.maximtereshchenko.bloom.application;

final class Display {

    private final char startRowRegister;
    private final char startColumnRegister;
    private final int rows;

    Display(char startRowRegister, char startColumnRegister, int rows) {
        this.startRowRegister = startRowRegister;
        this.startColumnRegister = startColumnRegister;
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "D" + startRowRegister + startColumnRegister + rows;
    }
}

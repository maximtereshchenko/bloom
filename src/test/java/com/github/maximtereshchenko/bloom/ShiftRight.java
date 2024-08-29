package com.github.maximtereshchenko.bloom;

final class ShiftRight {

    private final char from;
    private final char to;

    ShiftRight(char from, char c) {
        this.from = from;
        to = c;
    }

    @Override
    public String toString() {
        return "8%c%c6".formatted(to, from);
    }
}

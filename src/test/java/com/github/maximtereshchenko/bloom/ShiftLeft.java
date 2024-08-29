package com.github.maximtereshchenko.bloom;

final class ShiftLeft {

    private final char from;
    private final char to;

    ShiftLeft(char from, char c) {
        this.from = from;
        to = c;
    }

    @Override
    public String toString() {
        return "8%c%cE".formatted(to, from);
    }
}

package com.github.maximtereshchenko.bloom.domain;

import java.util.List;
import java.util.stream.Collectors;

final class Hexadecimal {

    private final List<HexadecimalSymbol> symbols;

    private Hexadecimal(List<HexadecimalSymbol> symbols) {
        this.symbols = symbols;
    }

    Hexadecimal(HexadecimalSymbol first, HexadecimalSymbol second) {
        this(List.of(first, second));
    }

    @Override
    public String toString() {
        return symbols.stream()
            .map(HexadecimalSymbol::value)
            .map(String::valueOf)
            .collect(Collectors.joining());
    }

    Byte asByte() {
        if (symbols.size() != 2) {
            throw new IllegalStateException();
        }
        return new Byte((byte) Integer.parseInt(toString(), 16));
    }

    HexadecimalSymbol first() {
        return symbols.getFirst();
    }

    HexadecimalSymbol last() {
        return symbols.getLast();
    }
}

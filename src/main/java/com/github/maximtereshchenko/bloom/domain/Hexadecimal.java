package com.github.maximtereshchenko.bloom.domain;

import java.util.ArrayList;
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

    Hexadecimal(HexadecimalSymbol first, HexadecimalSymbol second, HexadecimalSymbol third) {
        this(List.of(first, second, third));
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
        return Byte.from(Integer.parseInt(toString(), 16));
    }

    MemoryAddress memoryAddress() {
        if (symbols.size() != 3) {
            throw new IllegalStateException();
        }
        return MemoryAddress.from(Integer.parseInt(toString(), 16));
    }

    HexadecimalSymbol first() {
        return symbols.getFirst();
    }

    HexadecimalSymbol last() {
        return symbols.getLast();
    }

    Hexadecimal concat(Hexadecimal hexadecimal) {
        var copy = new ArrayList<>(symbols);
        copy.addAll(hexadecimal.symbols);
        if (copy.size() > 4) {
            throw new IllegalStateException();
        }
        return new Hexadecimal(copy);
    }
}

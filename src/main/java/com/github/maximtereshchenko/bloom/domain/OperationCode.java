package com.github.maximtereshchenko.bloom.domain;

final class OperationCode {

    private final Bits first;
    private final Bits second;

    OperationCode(Bits first, Bits second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first.hexadecimal() + second.hexadecimal();
    }

    HexadecimalSymbol nibble(int index) {
        return HexadecimalSymbol.from(toString().charAt(index));
    }
}

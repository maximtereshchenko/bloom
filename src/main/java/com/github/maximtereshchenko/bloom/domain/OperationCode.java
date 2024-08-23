package com.github.maximtereshchenko.bloom.domain;

final class OperationCode {

    private final Byte first;
    private final Byte second;

    OperationCode(Byte first, Byte second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first.hexadecimal().concat(second.hexadecimal()).toString();
    }

    HexadecimalSymbol firstNibble() {
        return first.hexadecimal().first();
    }

    HexadecimalSymbol middleLeftNibble() {
        return first.hexadecimal().last();
    }

    HexadecimalSymbol middleRightNibble() {
        return second.hexadecimal().first();
    }

    HexadecimalSymbol lastNibble() {
        return second.hexadecimal().last();
    }
}

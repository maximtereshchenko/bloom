package com.github.maximtereshchenko.bloom.domain;

final class OperationCode {

    private final UnsignedByte first;
    private final UnsignedByte second;

    OperationCode(UnsignedByte first, UnsignedByte second) {
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

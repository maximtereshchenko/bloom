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

    HexadecimalSymbol nibble(int index) {
        return switch (index) {
            case 0 -> first.hexadecimal().first();
            case 1 -> first.hexadecimal().last();
            case 2 -> second.hexadecimal().first();
            case 3 -> second.hexadecimal().last();
            default -> throw new IndexOutOfBoundsException(index);
        };
    }
}

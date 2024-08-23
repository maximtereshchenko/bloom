package com.github.maximtereshchenko.bloom.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

final class UnsignedByte implements Comparable<UnsignedByte> {

    private static final int PRIMITIVE_MAX = 0xFF;
    static final UnsignedByte MAX = from(PRIMITIVE_MAX);

    private final int value;

    private UnsignedByte(int value) {
        this.value = value;
    }

    UnsignedByte() {
        this(0);
    }

    static UnsignedByte from(int value) {
        return new UnsignedByte(value & PRIMITIVE_MAX);
    }

    static UnsignedByte from(String bits) {
        return from(Integer.parseInt(bits, 2));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof UnsignedByte other &&
            value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(UnsignedByte other) {
        return Integer.compare(value, other.value);
    }

    Hexadecimal hexadecimal() {
        var string = "%02X".formatted(value);
        return new Hexadecimal(
            HexadecimalSymbol.from(string.charAt(0)),
            HexadecimalSymbol.from(string.charAt(1))
        );
    }

    UnsignedByte moduloRemainder(UnsignedByte other) {
        return UnsignedByte.from(value % other.value);
    }

    UnsignedByte sum(UnsignedByte other) {
        return UnsignedByte.from(value + other.value);
    }

    UnsignedByte difference(UnsignedByte other) {
        return UnsignedByte.from(value - other.value);
    }

    UnsignedByte or(UnsignedByte other) {
        return from(value | other.value);
    }

    UnsignedByte and(UnsignedByte other) {
        return from(value & other.value);
    }

    UnsignedByte xor(UnsignedByte other) {
        return from(value ^ other.value);
    }

    UnsignedByte shiftedRight() {
        return from(value >> 1);
    }

    UnsignedByte shiftedLeft() {
        return from(value << 1);
    }

    List<UnsignedByte> rangeTo(UnsignedByte upperBound) {
        return IntStream.range(value, upperBound.value)
            .mapToObj(UnsignedByte::from)
            .toList();
    }

    List<Boolean> bits() {
        return IntStream.range(0, 8)
            .mapToObj(index -> (value & (1 << 7 - index)) != 0)
            .toList();
    }

    List<UnsignedByte> binaryCodedDecimal() {
        return "%03d".formatted(value)
            .chars()
            .map(Character::getNumericValue)
            .mapToObj(UnsignedByte::from)
            .toList();
    }

    int primitive() {
        return value;
    }
}

package com.github.maximtereshchenko.bloom.domain;

final class Bits {

    private final byte value;

    Bits(byte value) {
        this.value = value;
    }

    static Bits from(String bits) {
        return new Bits((byte) Integer.parseInt(bits, 2));
    }

    @Override
    public String toString() {
        var builder = new StringBuilder(Integer.toBinaryString(value));
        builder.insert(0, "0".repeat(Math.max(0, 8 - builder.length())));
        return builder.substring(builder.length() - 8);
    }

    byte value() {
        return value;
    }

    String hexadecimal() {
        return "%02X".formatted(value);
    }

    boolean has(int index) {
        return toString().charAt(index) == '1';
    }
}

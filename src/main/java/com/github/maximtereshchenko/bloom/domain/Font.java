package com.github.maximtereshchenko.bloom.domain;

import java.util.List;
import java.util.stream.Stream;

final class Font {

    void load(byte[] bytes, MemoryAddress start) {
        var memoryAddress = start;
        for (var bits : bits()) {
            bytes[memoryAddress.value()] = bits.value();
            memoryAddress = memoryAddress.next();
        }
    }

    private String withZeros(String ones) {
        var builder = new StringBuilder(ones.replace(' ', '0'));
        builder.append("0".repeat(8 - builder.length()));
        return builder.toString();
    }

    private List<Bits> bits() {
        return Stream.of(
                """
                    1111
                    1  1
                    1  1
                    1  1
                    1111
                    """,
                """
                      1
                     11
                      1
                      1
                     111
                    """,
                """
                    1111
                       1
                    1111
                    1
                    1111
                    """,
                """
                    1111
                       1
                    1111
                       1
                    1111
                    """,
                """
                    1  1
                    1  1
                    1111
                       1
                       1
                    """,
                """
                    1111
                    1
                    1111
                       1
                    1111
                    """,
                """
                    1111
                    1
                    1111
                    1  1
                    1111
                    """,
                """
                    1111
                       1
                      1
                     1
                     1
                    """,
                """
                    1111
                    1  1
                    1111
                    1  1
                    1111
                    """,
                """
                    1111
                    1  1
                    1111
                       1
                    1111
                    """,
                """
                    1111
                    1  1
                    1111
                    1  1
                    1  1
                    """,
                """
                    111
                    1  1
                    111
                    1  1
                    111
                    """,
                """
                    1111
                    1
                    1
                    1
                    1111
                    """,
                """
                    111
                    1  1
                    1  1
                    1  1
                    111
                    """,
                """
                    1111
                    1
                    1111
                    1
                    1111
                    """,
                """
                    1111
                    1
                    1111
                    1
                    1
                    """
            )
            .map(String::stripTrailing)
            .map(symbol -> symbol.split(System.lineSeparator()))
            .flatMap(Stream::of)
            .map(this::withZeros)
            .map(Bits::from)
            .toList();
    }
}

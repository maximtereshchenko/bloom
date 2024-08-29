package com.github.maximtereshchenko.bloom.domain;

import java.util.stream.Stream;

final class Font {

    UnsignedByte[] bytes() {
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
            .map(UnsignedByte::from)
            .toArray(UnsignedByte[]::new);
    }

    private String withZeros(String ones) {
        var builder = new StringBuilder(ones.replace(' ', '0'));
        builder.append("0".repeat(8 - builder.length()));
        return builder.toString();
    }
}

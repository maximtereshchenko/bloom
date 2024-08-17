package com.github.maximtereshchenko.bloom.domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Registers {

    private final ProgramCounter programCounter = new ProgramCounter();
    private final Index index = new Index();
    private final Map<HexadecimalSymbol, Register<Byte>> generalPurpose = Stream.of(HexadecimalSymbol.values())
        .collect(
            Collectors.toMap(
                Function.identity(),
                symbol -> new GeneralPurpose(),
                (a, b) -> a,
                () -> new EnumMap<>(HexadecimalSymbol.class)
            )
        );

    ProgramCounter programCounter() {
        return programCounter;
    }

    Index index() {
        return index;
    }

    Register<Byte> generalPurpose(HexadecimalSymbol registerName) {
        return generalPurpose.get(registerName);
    }
}

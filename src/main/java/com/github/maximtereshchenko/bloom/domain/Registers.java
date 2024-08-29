package com.github.maximtereshchenko.bloom.domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Registers {

    private final ProgramCounter programCounter = new ProgramCounter();
    private final Index index = new Index();
    private final FlagRegister flagRegister = new FlagRegister();
    private final Map<HexadecimalSymbol, Register<UnsignedByte>> generalPurpose = Stream.of(
            HexadecimalSymbol.values())
        .collect(
            Collectors.toMap(
                Function.identity(),
                symbol -> symbol == HexadecimalSymbol.F ? flagRegister :
                    new GeneralPurposeRegister(),
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

    FlagRegister flagRegister() {
        return flagRegister;
    }

    Register<UnsignedByte> generalPurpose(HexadecimalSymbol registerName) {
        return generalPurpose.get(registerName);
    }
}

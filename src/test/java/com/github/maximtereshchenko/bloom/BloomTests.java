package com.github.maximtereshchenko.bloom;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

final class BloomTests {

    static IntStream widthIndexes() {
        return IntStream.rangeClosed(0, 32);
    }

    @ParameterizedTest
    @ValueSource(chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'})
    void givenDisplayFontCharacter_thenCharacterDisplayed(char character) {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('1', "0" + character),
                new SetFontCharacter('1'),
                new Display('0', '0', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation(character);
    }

    @Test
    void givenDisplaySamePixelsTwice_thenEmptyDisplay() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('1', "00"),
                new SetFontCharacter('1'),
                new Display('0', '0', 5),
                new Display('0', '0', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("widthIndexes")
    void givenDisplayWithRowRegister_thenCharacterDisplayedStartingAtRow(int row) {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "%02X".formatted(row)),
                new SetRegisterValue('2', "00"),
                new SetFontCharacter('2'),
                new Display('0', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation(row);
    }

    @Test
    void givenPixelDisabledDuringDisplay_thenFlagRegisterSetToOne() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('1', "00"),
                new SetFontCharacter('1'),
                new Display('0', '0', 5),
                new Display('0', '0', 5),
                new Display('F', 'F', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }
}

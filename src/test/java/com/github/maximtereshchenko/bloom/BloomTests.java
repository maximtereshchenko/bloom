package com.github.maximtereshchenko.bloom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class BloomTests {

    @ParameterizedTest
    @ValueSource(chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'})
    void givenDisplayFontCharacter_thenCharacterDisplayed(char character) {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('1', "0" + character),
                new SetFontCharacter('1'),
                new Display(0, 0, 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation(String.valueOf(character));
    }

    @Test
    void givenDisplaySamePixelsTwice_thenEmptyDisplay() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('1', "00"),
                new SetFontCharacter('1'),
                new Display(0, 0, 5),
                new Display(0, 0, 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }
}

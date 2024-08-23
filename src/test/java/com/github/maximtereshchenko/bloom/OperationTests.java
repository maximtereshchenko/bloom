package com.github.maximtereshchenko.bloom;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

final class OperationTests {

    static IntStream widthIndexes() {
        return IntStream.rangeClosed(0, 64);
    }

    static IntStream heightIndexes() {
        return IntStream.rangeClosed(0, 32);
    }

    static Stream<Object> skipConditionallyOperations() {
        return Stream.of(
            new SkipIfRegisterValueEquals('0', "00"),
            new SkipIfRegisterValueNotEqual('0', "01"),
            new SkipIfRegisterValuesEqual('0', '1'),
            new SkipIfRegisterValuesNotEqual('0', '2')
        );
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
                new SetFontCharacter('0'),
                new Display('0', '0', 5),
                new Display('0', '0', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("heightIndexes")
    void givenDisplayWithRowRegister_thenCharacterDisplayedStartingAtRow(int row) {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "%02X".formatted(row)),
                new SetFontCharacter('1'),
                new Display('0', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation(row);
    }

    @ParameterizedTest
    @MethodSource("widthIndexes")
    void givenDisplayWithColumnRegister_thenCharacterDisplayedStartingAtColumn(int column) {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "%02X".formatted(column)),
                new SetFontCharacter('1'),
                new Display('1', '0', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation(column);
    }

    @Test
    void givenPixelDisabledDuringDisplay_thenFlagRegisterSetToOne() {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Display('0', '0', 5),
                new Display('0', '0', 5),
                new Display('F', 'F', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenClearDisplay_thenEmptyDisplay() {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Display('0', '0', 5),
                new ClearDisplay()
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenJump_thenInstructionSkipped() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('1', "01"),
                new SetFontCharacter('0'),
                new Jump(new MemoryAddress(4)),
                new SetFontCharacter('1'),//should be skipped
                new Display('0', '0', 5)
            )
            .whenExecuteInstructions(4)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenAddValueToRegister_thenRegisterHasSum() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "01"),
                new AddValueToRegister('0', "02"),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSetIndexRegisterValue_thenValueFromMemoryAddressDisplayed() {
        new Dsl()
            .givenProgram(
                new SetIndexRegisterValue(new MemoryAddress(2)),
                new Display('0', '0', 2),
                "FFFF"
            )
            .whenExecuteInstructions(2)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSubroutine_thenSubroutineExecuted() {
        new Dsl()
            .givenProgram(
                new CallSubroutine(new MemoryAddress(2)),
                new Display('0', '0', 5),
                new SetFontCharacter('0'),
                new ReturnFromSubroutine()
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("skipConditionallyOperations")
    void givenSkipConditionally_thenNextInstructionSkipped(Object instruction) {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new SetRegisterValue('2', "01"),
                instruction,
                new SetFontCharacter('2'),//should be skipped
                new Display('0', '0', 5)
            )
            .whenExecuteInstructions(4)
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("skipConditionallyOperations")
    void givenSkipConditionally_thenNextInstructionNotSkipped(Object instruction) {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new SetRegisterValue('2', "01"),
                new SetRegisterValue('0', "01"),
                instruction,
                new SetFontCharacter('2'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenCopyRegisterValue_thenRegisterHasSameValue() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "01"),
                new CopyRegisterValue('0', '1'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenBinaryOr_thenRegisterHasDisjunctionValue() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "01"),
                new SetRegisterValue('1', "02"),
                new BinaryOr('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenBinaryAnd_thenRegisterHasConjunctionValue() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "01"),
                new SetRegisterValue('1', "03"),
                new BinaryAnd('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenBinaryXor_thenRegisterHasExclusiveOrValue() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "01"),
                new SetRegisterValue('1', "03"),
                new BinaryXor('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSumRegisterValues_thenRegisterHasSum() {
        new Dsl()
            .givenProgram(
                new SetRegisterValue('0', "01"),
                new SetRegisterValue('1', "02"),
                new SumRegisterValues('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }
}

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
            new SkipIfRegisterValueEqual('0', "00"),
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
                new Set('1', "0" + character),
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
                new Set('0', "%02X".formatted(row)),
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
                new Set('0', "%02X".formatted(column)),
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
                new Set('1', "01"),
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
                new Set('0', "01"),
                new Add('0', "02"),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSetIndex_thenValueFromMemoryAddressDisplayed() {
        new Dsl()
            .givenProgram(
                new SetIndex(new MemoryAddress(2)),
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
                new Set('2', "01"),
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
                new Set('2', "01"),
                new Set('0', "01"),
                instruction,
                new SetFontCharacter('2'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenCopy_thenRegisterHasSameValue() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Copy('0', '1'),
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
                new Set('0', "01"),
                new Set('1', "02"),
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
                new Set('0', "01"),
                new Set('1', "03"),
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
                new Set('0', "01"),
                new Set('1', "03"),
                new BinaryXor('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSum_thenRegisterHasSum() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('1', "02"),
                new Sum('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSum_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new Sum('0', '1'),
                new SetFontCharacter('F'),
                new Display('0', '0', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSum_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "FF"),
                new Set('1', "01"),
                new Sum('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSubtract_thenRegisterHasDifference() {
        new Dsl()
            .givenProgram(
                new Set('0', "03"),
                new Set('1', "01"),
                new Subtract('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSubtract_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('1', "02"),
                new Subtract('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSubtract_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "02"),
                new Set('1', "01"),
                new Subtract('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRight_thenRegisterHasValueShiftedOneBitRight() {
        new Dsl()
            .givenProgram(
                new Set('0', "02"),
                new ShiftRight('0'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRight_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new ShiftRight('0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRight_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new ShiftRight('0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeft_thenRegisterHasValueShiftedOneBitLeft() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new ShiftLeft('0'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeft_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "80"),
                new ShiftLeft('0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeft_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new ShiftLeft('0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenConvertToBinaryCodedDecimal_thenMemoryHoldsBinaryCodedDecimal() {
        new Dsl()
            .givenProgram(
                new Set('0', "7B"),
                new SetIndex(new MemoryAddress(4)),
                new ConvertToBinaryCodedDecimal('0'),
                new Display('1', '1', 3)
            )
            .whenExecuteAllInstructions()
            .thenOutputMatchesExpectation();
    }
}

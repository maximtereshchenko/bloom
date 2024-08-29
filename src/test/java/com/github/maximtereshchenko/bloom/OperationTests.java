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

    static Stream<Object> binaryLogicalOperations() {
        return Stream.of(new BinaryAnd('0', '1'), new BinaryOr('0', '1'), new BinaryXor('0', '1'));
    }

    static Stream<Object> incrementingIndexOperations() {
        return Stream.of(new Store('1'), new Load(('1')));
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenJump_thenOperationSkipped() {
        new Dsl()
            .givenProgram(
                new Set('1', "01"),
                new SetFontCharacter('0'),
                new Jump(new MemoryAddress(4)),
                new SetFontCharacter('1'),//should be skipped
                new Display('0', '0', 5)
            )
            .whenExecuteOperations(4)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenJumpWithOffset_thenOperationsSkipped() {
        new Dsl()
            .givenProgram(
                new Set('0', "02"),
                new Set('1', "01"),
                new SetFontCharacter('2'),
                new JumpWithOffset(new MemoryAddress(5)),
                new SetFontCharacter('1'),//should be skipped
                new SetFontCharacter('1'),//should be skipped
                new Display('2', '2', 5)
            )
            .whenExecuteOperations(5)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenAdd_thenRegisterHasSum() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Add('0', "02"),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
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
            .whenExecuteOperations(2)
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
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("skipConditionallyOperations")
    void givenSkipConditionally_thenNextOperationSkipped(Object operation) {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Set('2', "01"),
                operation,
                new SetFontCharacter('2'),//should be skipped
                new Display('0', '0', 5)
            )
            .whenExecuteOperations(4)
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("skipConditionallyOperations")
    void givenSkipConditionally_thenNextOperationNotSkipped(Object operation) {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Set('2', "01"),
                new Set('0', "01"),
                operation,
                new SetFontCharacter('2'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSkipIfKeyPressed_thenNextOperationSkipped() {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Set('1', "01"),
                new Set('2', "0F"),
                new SkipIfKeyPressed('2'),
                new SetFontCharacter('1'),//should be skipped
                new Display('0', '0', 5)
            )
            .whenExecuteOperations(5)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSkipIfKeyPressed_thenNextOperationNotSkipped() {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Set('1', "01"),
                new SkipIfKeyPressed('1'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSkipIfKeyNotPressed_thenNextOperationSkipped() {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Set('1', "01"),
                new SkipIfKeyNotPressed('1'),
                new SetFontCharacter('1'),//should be skipped
                new Display('2', '2', 5)
            )
            .whenExecuteOperations(4)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSkipIfKeyNotPressed_thenNextOperationNotSkipped() {
        new Dsl()
            .givenProgram(
                new SetFontCharacter('0'),
                new Set('1', "01"),
                new Set('0', "0F"),
                new SkipIfKeyNotPressed('0'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSumWithFlagRegister_thenRegisterHasSum() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('F', "01"),
                new Sum('0', 'F'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSubtractEqualNumbers_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('1', "01"),
                new Subtract('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSubtractWithFlagRegister_thenRegisterHasDifference() {
        new Dsl()
            .givenProgram(
                new Set('0', "02"),
                new Set('F', "01"),
                new Subtract('0', 'F'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenNegativeSubtract_thenRegisterHasNegativeDifference() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('1', "03"),
                new NegativeSubtract('0', '1'),
                new SetFontCharacter('0'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenNegativeSubtract_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "02"),
                new Set('1', "01"),
                new NegativeSubtract('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenNegativeSubtract_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('1', "02"),
                new NegativeSubtract('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenNegativeSubtractEqualNumbers_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('1', "01"),
                new NegativeSubtract('0', '1'),
                new SetFontCharacter('F'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenNegativeSubtractWithFlagRegister_thenRegisterHasDifference() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new Set('F', "02"),
                new NegativeSubtract('0', 'F'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRight_thenRegisterHasValueShiftedOneBitRight() {
        new Dsl()
            .givenProgram(
                new Set('0', "02"),
                new ShiftRight('0', '1'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRight_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new ShiftRight('0', '0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRight_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new ShiftRight('0', '0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftRightWithFlagRegister_thenFlagRegisterOverriddenWithShiftedBit() {
        new Dsl()
            .givenProgram(
                new Set('F', "01"),
                new ShiftRight('F', 'F'),
                new SetFontCharacter('F'),
                new Display('0', '0', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeft_thenRegisterHasValueShiftedOneBitLeft() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new ShiftLeft('0', '1'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeft_thenFlagRegisterSet() {
        new Dsl()
            .givenProgram(
                new Set('0', "80"),
                new ShiftLeft('0', '0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeft_thenFlagRegisterNotSet() {
        new Dsl()
            .givenProgram(
                new ShiftLeft('0', '0'),
                new SetFontCharacter('F'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenShiftLeftWithFlagRegister_thenFlagRegisterOverriddenWithShiftedBit() {
        new Dsl()
            .givenProgram(
                new Set('F', "80"),
                new ShiftLeft('F', 'F'),
                new SetFontCharacter('F'),
                new Display('0', '0', 5)
            )
            .whenExecuteAllOperations()
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
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenStore_thenRegistersUpToSpecifiedStoredInMemory() {
        new Dsl()
            .givenProgram(
                new Set('0', "FF"),
                new Set('1', "FF"),
                new Set('2', "FF"),
                new SetIndex(new MemoryAddress(7)),
                new Store('1'),
                new SetIndex(new MemoryAddress(7)),
                new Display('3', '3', 3)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenLoad_thenRegistersUpToSpecifiedHaveValuesFromMemory() {
        new Dsl()
            .givenProgram(
                new SetIndex(new MemoryAddress(4)),
                new Load('1'),
                new SetFontCharacter('2'),
                new Display('0', '1', 5),
                "0101",
                "FF00"
            )
            .whenExecuteOperations(4)
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("incrementingIndexOperations")
    void givenIncrementingIndexOperation_thenIndexHasIncrementedValue(Object operation) {
        new Dsl()
            .givenProgram(
                new SetIndex(new MemoryAddress(3)),
                operation,
                new Display('0', '0', 1),
                "0000",
                "FF00"
            )
            .whenExecuteOperations(3)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenAddToIndex_thenIndexHasSum() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new SetIndex(new MemoryAddress(4)),
                new AddToIndex('0'),
                new Display('1', '1', 1),
                "00FF"
            )
            .whenExecuteOperations(4)
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenGetKey_thenRegisterHasKey() {
        new Dsl()
            .givenProgram(
                new GetKey('0'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSetDelayTimer_thenDelayTimerHasSetValue() {
        new Dsl()
            .givenProgram(
                new Set('0', "0F"),
                new SetDelayTimer('0'),
                new ReadDelayTimer('1'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenSetSoundTimer_thenSoundEnabled() {
        new Dsl()
            .givenProgram(
                new Set('0', "FF"),
                new SetSoundTimer('0')
            )
            .whenExecuteAllOperations()
            .thenSoundEnabled();
    }

    @Test
    void givenRandom_thenRegisterHasConjunctionValueWithRandom() {
        new Dsl()
            .givenProgram(
                new Random('0', "01"),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }

    @ParameterizedTest
    @MethodSource("binaryLogicalOperations")
    void givenBinaryLogicalOperation_thenFlagRegisterHasZero(Object operation) {
        new Dsl()
            .givenProgram(
                new Set('F', "02"),
                operation,
                new SetFontCharacter('F'),
                new Display('0', '0', 5)
            )
            .whenExecuteAllOperations()
            .thenOutputMatchesExpectation();
    }
}

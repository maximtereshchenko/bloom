package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.DecrementDelayTimerUseCase;
import com.github.maximtereshchenko.bloom.api.DecrementSoundTimerUseCase;
import com.github.maximtereshchenko.bloom.api.ExecuteNextOperationUseCase;
import org.junit.jupiter.api.Test;

final class TimerTests {

    @Test
    void givenDecrementSoundTimer_thenSoundDisabled() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new SetSoundTimer('0')
            )
            .when(
                ExecuteNextOperationUseCase::executeNextOperation,
                ExecuteNextOperationUseCase::executeNextOperation,
                DecrementSoundTimerUseCase::decrementSoundTimer
            )
            .thenSoundEnabled(false);
    }

    @Test
    void givenDecrementDelayTimer_thenDelayTimerDecremented() {
        new Dsl()
            .givenProgram(
                new Set('0', "01"),
                new SetDelayTimer('0'),
                new ReadDelayTimer('1'),
                new SetFontCharacter('1'),
                new Display('2', '2', 5)
            )
            .when(
                ExecuteNextOperationUseCase::executeNextOperation,
                ExecuteNextOperationUseCase::executeNextOperation,
                DecrementDelayTimerUseCase::decrementDelayTimer,
                ExecuteNextOperationUseCase::executeNextOperation,
                ExecuteNextOperationUseCase::executeNextOperation,
                ExecuteNextOperationUseCase::executeNextOperation
            )
            .thenOutputMatchesExpectation();
    }

    @Test
    void givenDecrementDelayTimer_thenDelayTimerNotOverflowed() {
        new Dsl()
            .givenProgram(
                new ReadDelayTimer('0'),
                new SetFontCharacter('0'),
                new Display('1', '1', 5)
            )
            .when(
                DecrementDelayTimerUseCase::decrementDelayTimer,
                ExecuteNextOperationUseCase::executeNextOperation,
                ExecuteNextOperationUseCase::executeNextOperation,
                ExecuteNextOperationUseCase::executeNextOperation
            )
            .thenOutputMatchesExpectation();
    }
}

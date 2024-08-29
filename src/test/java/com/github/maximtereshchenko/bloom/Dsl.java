package com.github.maximtereshchenko.bloom;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.api.ExecuteNextOperationUseCase;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.approvaltests.Approvals.verify;
import static org.assertj.core.api.Assertions.assertThat;

final class Dsl {

    Execution givenProgram(Object... hexadecimalBytes) {
        var display = new FakeDisplay();
        var sound = new FakeSound();
        return new Execution(
            new BloomFacade(
                bytes(hexadecimalBytes),
                display,
                new FakeKeypad(),
                sound,
                new FakeRandomness()
            ),
            display,
            sound,
            hexadecimalBytes.length
        );
    }

    private byte[] bytes(Object... hexadecimalBytes) {
        var program = new byte[hexadecimalBytes.length * 2];
        for (var i = 0; i < program.length; i += 2) {
            var hexadecimal = hexadecimalBytes[i / 2].toString();
            program[i] = toByte(hexadecimal.substring(0, 2));
            program[i + 1] = toByte(hexadecimal.substring(2));
        }
        return program;
    }

    private byte toByte(String hexadecimal) {
        return (byte) Integer.parseInt(hexadecimal, 16);
    }

    static final class Execution {

        private final BloomModule module;
        private final FakeDisplay display;
        private final FakeSound sound;
        private final int approximateOperationCount;

        Execution(
            BloomModule module,
            FakeDisplay display,
            FakeSound sound,
            int approximateOperationCount
        ) {
            this.module = module;
            this.display = display;
            this.sound = sound;
            this.approximateOperationCount = approximateOperationCount;
        }

        Result whenExecuteAllOperations() {
            return whenExecuteOperations(approximateOperationCount);
        }

        Result whenExecuteOperations(int count) {
            return when(
                Stream.<Consumer<BloomModule>>generate(() -> ExecuteNextOperationUseCase::executeNextOperation)
                    .limit(count)
                    .toList()
            );
        }

        @SafeVarargs
        final Result when(Consumer<BloomModule>... actions) {
            return when(List.of(actions));
        }

        private Result when(List<Consumer<BloomModule>> actions) {
            actions.forEach(action -> action.accept(module));
            return new Result(display, sound);
        }
    }

    static final class Result {

        private final FakeDisplay display;
        private final FakeSound sound;

        Result(FakeDisplay display, FakeSound sound) {
            this.display = display;
            this.sound = sound;
        }

        void thenSoundEnabled() {
            thenSoundEnabled(true);
        }

        void thenSoundEnabled(boolean expected) {
            assertThat(sound.isEnabled()).isEqualTo(expected);
        }

        void thenOutputMatchesExpectation(char parameter) {
            thenOutputMatchesExpectation(String.valueOf(parameter));
        }

        void thenOutputMatchesExpectation(int parameter) {
            thenOutputMatchesExpectation(String.valueOf(parameter));
        }

        void thenOutputMatchesExpectation(String... parameters) {
            verify(display, ApprovalsOptions.defaultConfiguration(parameters));
        }
    }
}

package com.github.maximtereshchenko.bloom;

import static org.approvaltests.Approvals.verify;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.api.ExecuteNextOperationUseCase;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.approvaltests.strings.Printable;

final class Dsl {

    Execution givenProgram(Object... hexadecimalBytes) {
        var sound = new FakeSound();
        return new Execution(
            new BloomFacade(bytes(hexadecimalBytes), new FakeKeypad(), sound),
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
        private final FakeSound sound;
        private final int approximateOperationCount;

        private Execution(BloomModule module, FakeSound sound, int approximateOperationCount) {
            this.module = module;
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
            return new Result(module, sound);
        }
    }

    static final class Result {

        private final BloomModule module;
        private final FakeSound sound;

        private Result(BloomModule module, FakeSound sound) {
            this.module = module;
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
            verify(
                new Printable<>(module.displayMask(), this::toString),
                ApprovalsOptions.defaultConfiguration(parameters)
            );
        }

        private String toString(boolean[][] displayMask) {
            var builder = new StringBuilder();
            for (var row : displayMask) {
                for (var isPixelEnabled : row) {
                    builder.append(isPixelEnabled ? '*' : ' ');
                }
                builder.append(System.lineSeparator());
            }
            return builder.toString();
        }
    }
}

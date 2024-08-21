package com.github.maximtereshchenko.bloom.application;

import static org.approvaltests.Approvals.verify;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.Junit5Reporter;
import org.approvaltests.strings.Printable;

final class Dsl {

    Execution givenProgram(String name, int count) throws URISyntaxException, IOException {
        var bytes = Files.readAllBytes(
            Path.of(
                Objects.requireNonNull(
                        Thread.currentThread()
                            .getContextClassLoader()
                            .getResource(name)
                    )
                    .toURI()
            )
        );
        return givenProgram(bytes, count);
    }

    Execution givenProgram(Object... hexadecimalBytes) {
        return givenProgram(bytes(hexadecimalBytes), hexadecimalBytes.length);
    }

    private Execution givenProgram(byte[] bytes, int count) {
        return new Execution(new BloomFacade(bytes), count);
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
        private final int count;

        private Execution(BloomModule module, int count) {
            this.module = module;
            this.count = count;
        }

        Result whenExecuteAllInstructions() {
            return whenExecuteInstructions(count);
        }

        Result whenExecuteInstructions(int count) {
            for (var i = 0; i < count; i++) {
                module.executeNextInstruction();
            }
            return new Result(module);
        }
    }

    static final class Result {

        private final BloomModule module;
        private final Options options = new Options()
            .withReporter(new Junit5Reporter())
            .forFile()
            .withNamer(new ResourcesNamer());

        private Result(BloomModule module) {
            this.module = module;
        }

        void thenOutputMatchesExpectation() {
            thenOutputMatchesExpectation(options);
        }

        void thenOutputMatchesExpectation(char parameter) {
            thenOutputMatchesExpectation(String.valueOf(parameter));
        }

        void thenOutputMatchesExpectation(int parameter) {
            thenOutputMatchesExpectation(String.valueOf(parameter));
        }

        private void thenOutputMatchesExpectation(String parameter) {
            thenOutputMatchesExpectation(options.forFile().withAdditionalInformation(parameter));
        }

        private void thenOutputMatchesExpectation(Options options) {
            verify(new Printable<>(module.displayMask(), this::toString), options);
        }

        private String toString(boolean[][] displayMask) {
            var builder = new StringBuilder();
            for (var row : displayMask) {
                for (var isPixelEnabled : row) {
                    builder.append(isPixelEnabled ? '*' : ' ');
                }
                builder.append(System.lineSeparator());
            }
            return builder.substring(0, builder.length() - 1);
        }
    }
}

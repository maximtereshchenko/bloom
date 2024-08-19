package com.github.maximtereshchenko.bloom.application;

import static org.approvaltests.Approvals.verify;

import com.github.maximtereshchenko.bloom.api.BloomModule;
import com.github.maximtereshchenko.bloom.domain.BloomFacade;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.Junit5Reporter;

final class Dsl {

    Execution givenProgram(Object... bytes) {
        var writer = new StringWriter();
        var display = new WriterDisplay(writer);
        return new Execution(
            new BloomFacade(program(bytes), display),
            display,
            writer,
            bytes.length
        );
    }

    private byte[] program(Object... bytes) {
        var program = new byte[bytes.length * 2];
        for (var i = 0; i < program.length; i += 2) {
            var hexadecimal = bytes[i / 2].toString();
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
        private final WriterDisplay display;
        private final Writer writer;
        private final int length;

        private Execution(BloomModule module, WriterDisplay display, Writer writer, int length) {
            this.module = module;
            this.display = display;
            this.writer = writer;
            this.length = length;
        }

        Result whenExecuteAllInstructions() throws IOException {
            return whenExecuteInstructions(length);
        }

        Result whenExecuteInstructions(int count) throws IOException {
            for (var i = 0; i < count; i++) {
                module.executeNextInstruction();
            }
            display.draw();
            return new Result(writer);
        }
    }

    static final class Result {

        private final Writer writer;
        private final Options options = new Options()
            .withReporter(new Junit5Reporter())
            .forFile()
            .withNamer(new ResourcesNamer());

        private Result(Writer writer) {
            this.writer = writer;
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
            verify(writer, options);
        }
    }
}

package com.github.maximtereshchenko.bloom.application;

import static org.approvaltests.Approvals.verify;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.maximtereshchenko.bloom.ApprovalsOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class ApplicationTests {

    private static final String MOVE_CURSOR_34_LINES_UP = "\033[34F";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @ParameterizedTest
    @ValueSource(strings = {"1-chip8-logo.ch8", "2-ibm-logo.ch8", "3-opcodes.ch8"})
    void givenProgram_thenProgramExecutedSuccessfully(String program) throws Exception {
        try (var application = application(program)) {
            application.start();

            await().atMost(Duration.ofSeconds(1)).untilAsserted(() -> {
                var output = outputStream.toString(StandardCharsets.UTF_8);
                var start = output.lastIndexOf(MOVE_CURSOR_34_LINES_UP);
                assertNotEquals(-1, start);
                verify(
                    output.substring(start + MOVE_CURSOR_34_LINES_UP.length()),
                    ApprovalsOptions.withParameter(program)
                );
            });
        }
    }

    private Application application(String program) throws URISyntaxException, IOException {
        return Application.from(
            new PrintStream(outputStream),
            Path.of(
                Objects.requireNonNull(
                        Thread.currentThread()
                            .getContextClassLoader()
                            .getResource(program)
                    )
                    .toURI()
            )
        );
    }
}

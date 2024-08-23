package com.github.maximtereshchenko.bloom.application;

import static org.approvaltests.Approvals.verify;
import static org.awaitility.Awaitility.await;

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

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @ParameterizedTest
    @ValueSource(strings = {"ibm-logo.ch8", "opcodes.ch8"})
    void givenProgram_thenProgramExecutedSuccessfully(String program) throws Exception {
        try (var application = application(program)) {
            application.start();

            await().atMost(Duration.ofSeconds(3)).untilAsserted(() -> {
                var output = outputStream.toString(StandardCharsets.UTF_8);
                verify(output.substring(output.lastIndexOf("\033[34F")), ApprovalsOptions.withParameter(program));
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

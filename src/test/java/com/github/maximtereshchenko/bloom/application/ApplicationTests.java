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
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

final class ApplicationTests {

    @Test
    void givenIbmLogoProgram_thenIbmLogoDisplayed() throws URISyntaxException, IOException, InterruptedException {
        var outputStream = new ByteArrayOutputStream();
        var application = Application.from(
            new PrintStream(outputStream),
            Path.of(
                Objects.requireNonNull(
                        Thread.currentThread()
                            .getContextClassLoader()
                            .getResource("ibm-logo.ch8")
                    )
                    .toURI()
            )
        );
        var eraseInLineSequence = "\033[2k".repeat(33);
        Approvals.settings().allowMultipleVerifyCallsForThisMethod();
        application.start();

        await().atMost(Duration.ofSeconds(3)).untilAsserted(() -> {
            var output = outputStream.toString(StandardCharsets.UTF_8);
            var trimmed = output.substring(0, output.lastIndexOf(eraseInLineSequence));
            verify(
                trimmed.substring(trimmed.lastIndexOf(eraseInLineSequence)),
                ApprovalsOptions.defaultConfiguration()
            );
        });
        application.stop();
    }
}

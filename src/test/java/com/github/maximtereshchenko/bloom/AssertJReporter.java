package com.github.maximtereshchenko.bloom;

import org.approvaltests.core.ApprovalFailureReporter;

import java.io.UncheckedIOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

final class AssertJReporter implements ApprovalFailureReporter {

    @Override
    public boolean report(String received, String approved) {
        var approvedPath = Paths.get(approved);
        assertThat(approvedPath).exists();
        var receivedPath = Paths.get(received);
        try {
            assertThat(receivedPath).hasSameTextualContentAs(approvedPath);
        } catch (UncheckedIOException e) {
            assertThat(receivedPath).hasSameBinaryContentAs(approvedPath);
        }
        return true;
    }
}

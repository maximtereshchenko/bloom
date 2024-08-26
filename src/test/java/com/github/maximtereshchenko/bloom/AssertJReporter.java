package com.github.maximtereshchenko.bloom;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import org.approvaltests.core.ApprovalFailureReporter;

final class AssertJReporter implements ApprovalFailureReporter {

    @Override
    public boolean report(String received, String approved) {
        var approvedPath = Paths.get(approved);
        assertThat(approvedPath).exists();
        assertThat(Paths.get(received)).hasSameBinaryContentAs(approvedPath);
        return true;
    }
}

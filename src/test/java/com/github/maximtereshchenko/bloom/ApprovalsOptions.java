package com.github.maximtereshchenko.bloom;

import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.Junit5Reporter;

public final class ApprovalsOptions {

    public static Options defaultConfiguration() {
        FileApprover.tracker.addAllowedDuplicates(file -> true);
        return new Options()
            .withReporter(new Junit5Reporter())
            .forFile()
            .withNamer(new ResourcesNamer());
    }

    public static Options withParameter(String parameter) {
        return defaultConfiguration().forFile().withAdditionalInformation(parameter);
    }
}

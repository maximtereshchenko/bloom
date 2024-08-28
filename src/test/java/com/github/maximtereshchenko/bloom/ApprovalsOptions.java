package com.github.maximtereshchenko.bloom;

import com.spun.util.logger.SimpleLogger;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.Options;

public final class ApprovalsOptions {

    public static Options defaultConfiguration(String... parameters) {
        SimpleLogger.logToString();
        FileApprover.tracker.addAllowedDuplicates(file -> true);
        var namer = new ResourcesNamer();
        for (var parameter : parameters) {
            namer = namer.addAdditionalInformation(parameter);
        }
        return new Options()
            .withReporter(new AssertJReporter())
            .forFile()
            .withNamer(namer);
    }
}

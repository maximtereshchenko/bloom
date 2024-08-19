package com.github.maximtereshchenko.bloom.application;

import java.io.File;
import java.nio.file.Paths;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.writers.Writer;

final class ResourcesNamer implements ApprovalNamer {

    private final ApprovalNamer delegate;

    private ResourcesNamer(ApprovalNamer delegate) {
        this.delegate = delegate;
    }

    ResourcesNamer() {
        this(new StackTraceNamer());
    }

    @Override
    public File getReceivedFile(String extensionWithDot) {
        return delegate.getReceivedFile(extensionWithDot);
    }

    @Override
    public File getApprovedFile(String extensionWithDot) {
        return Paths.get("src", "test", "resources", getApprovalName() + Writer.approved + extensionWithDot)
            .toFile();
    }

    @Override
    public ApprovalNamer addAdditionalInformation(String info) {
        return new ResourcesNamer(delegate.addAdditionalInformation(info));
    }

    @Override
    public String getApprovalName() {
        return delegate.getApprovalName();
    }

    @Override
    public String getSourceFilePath() {
        return delegate.getSourceFilePath();
    }
}

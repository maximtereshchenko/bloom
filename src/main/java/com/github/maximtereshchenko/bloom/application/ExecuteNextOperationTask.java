package com.github.maximtereshchenko.bloom.application;

import com.github.maximtereshchenko.bloom.api.ExecuteNextOperationUseCase;

final class ExecuteNextOperationTask extends LoggingTask {

    private final ExecuteNextOperationUseCase useCase;

    ExecuteNextOperationTask(ExecuteNextOperationUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    void execute() {
        useCase.executeNextOperation();
    }
}

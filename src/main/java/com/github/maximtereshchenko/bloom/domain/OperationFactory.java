package com.github.maximtereshchenko.bloom.domain;

import java.util.Optional;

interface OperationFactory {

    Optional<Operation> operation(OperationCode operationCode);
}

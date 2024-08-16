package com.github.maximtereshchenko.bloom.domain;

interface OperationFactory {

    boolean supports(OperationCode operationCode);

    Operation operation(OperationCode operationCode);
}

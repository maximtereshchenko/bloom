package com.github.maximtereshchenko.bloom.application;

import java.io.IOException;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) throws IOException {
        Application.from(System.out, Paths.get(args[0])).start();
    }
}

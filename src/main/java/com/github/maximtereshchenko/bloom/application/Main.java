package com.github.maximtereshchenko.bloom.application;

import java.io.IOException;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) throws IOException {
        var application = Application.from(System.out, Paths.get(args[0]));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> close(application)));
        application.start();
    }

    private static void close(Application application) {
        try {
            application.close();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

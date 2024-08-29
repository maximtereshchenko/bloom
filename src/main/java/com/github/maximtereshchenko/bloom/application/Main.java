package com.github.maximtereshchenko.bloom.application;

import javax.swing.*;
import java.nio.file.Paths;

final class Main {

    public static void main(String[] args) throws Exception {
        var application = JFrameApplication.from(Paths.get(args[0]));
        application.start();
        SwingUtilities.invokeLater(() -> application.setVisible(true));
    }
}

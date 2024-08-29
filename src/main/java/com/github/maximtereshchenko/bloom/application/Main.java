package com.github.maximtereshchenko.bloom.application;

import javax.swing.*;

final class Main {

    public static void main(String[] args) throws Exception {
        var application = JFrameApplication.from(args[0]);
        application.start();
        SwingUtilities.invokeLater(() -> application.setVisible(true));
    }
}

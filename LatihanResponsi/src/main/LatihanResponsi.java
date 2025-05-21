package main;

import view.MainForm;

public class LatihanResponsi {
    public static void main(String[] args) {
        // Launch the main form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
}
package main;

import javax.swing.SwingUtilities;
import views.RecruitmentFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecruitmentFrame().setVisible(true);
        });
    }
}

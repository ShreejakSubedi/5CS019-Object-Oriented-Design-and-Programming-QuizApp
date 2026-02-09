package quizapp.app;

import javax.swing.SwingUtilities;
import quizapp.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
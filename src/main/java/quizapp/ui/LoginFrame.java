package quizapp.ui;

import quizapp.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField txtUsername = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Quiz App - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 220);
        setLocationRelativeTo(null);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> {
            new RegisterFrame(this).setVisible(true);
            setVisible(false);
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(btnRegister);
        panel.add(btnLogin);

        setContentPane(panel);
    }

    private void doLogin() {
        try {
            String u = txtUsername.getText().trim();
            String p = new String(txtPassword.getPassword());

            int competitorId = authService.login(u, p);
            if (competitorId < 0) {
                JOptionPane.showMessageDialog(this, "Invalid login.");
                return;
            }

            new MainMenuFrame(competitorId).setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
package quizapp.ui;

import quizapp.dao.CompetitorDAO;
import quizapp.dao.UserDAO;
import quizapp.model.Competitor;
import quizapp.model.Level;
import quizapp.model.Name;
import quizapp.util.PasswordUtil;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final JFrame loginFrame;

    private final JTextField txtCompetitorId = new JTextField(10);
    private final JTextField txtFirstName = new JTextField(10);
    private final JTextField txtMiddleName = new JTextField(10);
    private final JTextField txtLastName = new JTextField(10);
    private final JComboBox<Level> cmbLevel = new JComboBox<>(Level.values());
    private final JTextField txtAge = new JTextField(10);

    private final JTextField txtUsername = new JTextField(10);
    private final JPasswordField txtPassword = new JPasswordField(10);

    private final CompetitorDAO competitorDAO = new CompetitorDAO();
    private final UserDAO userDAO = new UserDAO();

    public RegisterFrame(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("Quiz App - Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(null);

        JButton btnBack = new JButton("Back");
        JButton btnCreate = new JButton("Create Account");

        btnBack.addActionListener(e -> {
            loginFrame.setVisible(true);
            dispose();
        });
        btnCreate.addActionListener(e -> doRegister());

        JPanel form = new JPanel(new GridLayout(9, 2, 10, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Competitor ID (int):"));
        form.add(txtCompetitorId);
        form.add(new JLabel("First name:"));
        form.add(txtFirstName);
        form.add(new JLabel("Middle name (optional):"));
        form.add(txtMiddleName);
        form.add(new JLabel("Last name:"));
        form.add(txtLastName);
        form.add(new JLabel("Level:"));
        form.add(cmbLevel);
        form.add(new JLabel("Age:"));
        form.add(txtAge);
        form.add(new JLabel("Username:"));
        form.add(txtUsername);
        form.add(new JLabel("Password:"));
        form.add(txtPassword);

        form.add(btnBack);
        form.add(btnCreate);

        setContentPane(form);
    }

    private void doRegister() {
        try {
            int competitorId = Integer.parseInt(txtCompetitorId.getText().trim());
            int age = Integer.parseInt(txtAge.getText().trim());

            Name name = new Name(
                txtFirstName.getText().trim(),
                txtMiddleName.getText().trim(),
                txtLastName.getText().trim()
            );

            Level level = (Level) cmbLevel.getSelectedItem();

            int[] initialScores = {0,0,0,0,0};
            Competitor c = new Competitor(competitorId, name, level, age, initialScores);

            competitorDAO.createCompetitor(c);

            String username = txtUsername.getText().trim();
            String passHash = PasswordUtil.sha256(new String(txtPassword.getPassword()));
            userDAO.createUser(username, passHash, competitorId);

            JOptionPane.showMessageDialog(this, "Account created! You can login now.");
            loginFrame.setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Register error: " + ex.getMessage());
        }
    }
}
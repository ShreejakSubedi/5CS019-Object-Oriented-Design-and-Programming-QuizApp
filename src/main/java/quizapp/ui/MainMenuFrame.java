package quizapp.ui;

import quizapp.dao.CompetitorDAO;
import quizapp.model.Competitor;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    private final int loggedInCompetitorId;
    private final CompetitorDAO competitorDAO = new CompetitorDAO();

    public MainMenuFrame(int loggedInCompetitorId) {
        this.loggedInCompetitorId = loggedInCompetitorId;

        setTitle("Quiz App - Main Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 320);
        setLocationRelativeTo(null);

        JButton btnStartQuiz = new JButton("Start Quiz (5 Questions)");
        JButton btnViewReport = new JButton("View Report");
        JButton btnFindShort = new JButton("Find Competitor (Short Details)");
        JButton btnFindFull = new JButton("Find Competitor (Full Details)");
        JButton btnLogout = new JButton("Logout");

        btnStartQuiz.addActionListener(e -> {
            new QuizFrame(loggedInCompetitorId, this).setVisible(true);
            setVisible(false);
        });

        btnViewReport.addActionListener(e -> {
            new ReportFrame(this).setVisible(true);
            setVisible(false);
        });

        btnFindShort.addActionListener(e -> findCompetitorShortDetails());
        btnFindFull.addActionListener(e -> findCompetitorFullDetails());

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(btnStartQuiz);
        panel.add(btnViewReport);
        panel.add(btnFindShort);
        panel.add(btnFindFull);
        panel.add(btnLogout);

        setContentPane(panel);
    }

    private Integer askForCompetitorId() {
        String input = JOptionPane.showInputDialog(this, "Enter Competitor ID:");
        if (input == null) return null; // user pressed cancel

        input = input.trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Competitor ID cannot be empty.");
            return null;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Competitor ID must be a number.");
            return null;
        }
    }

    private void findCompetitorShortDetails() {
        try {
            Integer id = askForCompetitorId();
            if (id == null) return;

            Competitor c = competitorDAO.getCompetitorById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "No competitor found with ID " + id);
                return;
            }

            // PDF requirement: short details by ID
            JOptionPane.showMessageDialog(this, c.getShortDetails());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void findCompetitorFullDetails() {
        try {
            Integer id = askForCompetitorId();
            if (id == null) return;

            Competitor c = competitorDAO.getCompetitorById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "No competitor found with ID " + id);
                return;
            }

            // Also demonstrate PDF required full details method
            JOptionPane.showMessageDialog(this, c.getFullDetails());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
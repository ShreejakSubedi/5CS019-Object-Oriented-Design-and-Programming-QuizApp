package quizapp.ui;

import quizapp.dao.CompetitorDAO;
import quizapp.model.Competitor;
import quizapp.model.Question;
import quizapp.service.QuizService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizFrame extends JFrame {

    private final int competitorId;
    private final JFrame parent;

    private final CompetitorDAO competitorDAO = new CompetitorDAO();
    private final QuizService quizService = new QuizService();

    private Competitor competitor;               // loaded from DB
    private List<Question> questions;            // chosen by level

    private int index = 0;
    private final int[] scores = new int[5];     // must stay 5 (score1..score5)

    private final JLabel lblHeader = new JLabel();
    private final JLabel lblQuestion = new JLabel();

    private final JRadioButton[] optionButtons = new JRadioButton[4];
    private final ButtonGroup group = new ButtonGroup();

    private final JButton btnNext = new JButton("Next");
    private final JButton btnBack = new JButton("Back to Menu");

    public QuizFrame(int competitorId, JFrame parent) {
        this.competitorId = competitorId;
        this.parent = parent;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 360);
        setLocationRelativeTo(null);

        // UI layout
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 6, 6));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            group.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        btnBack.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        btnNext.addActionListener(e -> nextOrFinish());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnBack);
        bottom.add(btnNext);

        JPanel top = new JPanel(new GridLayout(2, 1));
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        top.add(lblHeader);
        top.add(lblQuestion);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        main.add(top, BorderLayout.NORTH);
        main.add(optionsPanel, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);
        setContentPane(main);

        // Load competitor + level-based questions
        initQuiz();
    }

    private void initQuiz() {
        try {
            competitor = competitorDAO.getCompetitorById(competitorId);
            if (competitor == null) {
                JOptionPane.showMessageDialog(this, "Competitor not found. Please login again.");
                parent.setVisible(true);
                dispose();
                return;
            }

            questions = quizService.getFiveQuestions(competitor.getLevel());

            setTitle("Quiz App - " + competitor.getLevel() + " Quiz");
            lblHeader.setText("Competitor: " + competitor.getName().getFullName()
                    + " | Level: " + competitor.getLevel()
                    + " | Attempting 5 questions");

            loadQuestion();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading quiz: " + ex.getMessage());
            parent.setVisible(true);
            dispose();
        }
    }

    private void loadQuestion() {
        group.clearSelection();
        Question q = questions.get(index);

        lblQuestion.setText(q.getText());

        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(opts[i]);
        }

        btnNext.setText(index == 4 ? "Finish" : "Next");
    }

    private int selectedOptionIndex() {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) return i;
        }
        return -1;
    }

    private void nextOrFinish() {
        int chosen = selectedOptionIndex();
        if (chosen == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer.");
            return;
        }

        Question q = questions.get(index);
        scores[index] = (chosen == q.getCorrectIndex()) ? 1 : 0;

        if (index < 4) {
            index++;
            loadQuestion();
            return;
        }

        // Finished: save scores to DB
        try {
            competitorDAO.updateScores(competitorId, scores);

            int total = 0;
            for (int s : scores) total += s;

            JOptionPane.showMessageDialog(this,
                    "Quiz complete (" + competitor.getLevel() + ")!\nScore: " + total + "/5\nSaved to database.");

            parent.setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving quiz scores: " + ex.getMessage());
        }
    }
}
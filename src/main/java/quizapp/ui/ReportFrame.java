package quizapp.ui;

import quizapp.model.Competitor;
import quizapp.model.CompetitorList;
import quizapp.model.Level;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportFrame extends JFrame {

    private final JFrame parent;

    private final DefaultTableModel model;
    private final JTable table;

    private final JTextArea txtSummary = new JTextArea();

    public ReportFrame(JFrame parent) {
        this.parent = parent;

        setTitle("Quiz App - Scoreboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 550);
        setLocationRelativeTo(null);

        // Columns like your example screenshot
        // If you want EXACT screenshot columns, keep these.
        // If you also want Overall for the report requirement, add "Overall" at the end.
        String[] columns = {
                "Competitor ID", "Name", "Level",
                "Score1", "Score2", "Score3", "Score4", "Score5",
                "Overall" // remove this column if you want EXACT same as screenshot
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // scoreboard should not be editable
            }
        };

        table = new JTable(model);
        table.setRowHeight(24);

        JButton btnRefresh = new JButton("Refresh");
        JButton btnBack = new JButton("Back to Menu");

        btnRefresh.addActionListener(e -> loadScoreboard());
        btnBack.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.add(btnRefresh);
        top.add(btnBack);

        // Summary area (top performer + frequency) – useful for coursework Part 3
        txtSummary.setEditable(false);
        txtSummary.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createTitledBorder("Summary"));
        bottom.add(new JScrollPane(txtSummary), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(table),
                bottom
        );
        split.setResizeWeight(0.75);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        loadScoreboard();
    }

    private void loadScoreboard() {
        try {
            CompetitorList list = new CompetitorList();
            list.loadFromDatabase();

            // clear table
            model.setRowCount(0);

            for (Competitor c : list.getAll()) {
                int[] s = c.getScoreArray();

                model.addRow(new Object[] {
                        c.getCompetitorId(),
                        c.getName().getFullName(),
                        prettyLevel(c.getLevel()),
                        s[0], s[1], s[2], s[3], s[4],
                        String.format("%.2f", c.getOverallScore()) // remove if you removed "Overall"
                });
            }

            // Build summary
            StringBuilder sb = new StringBuilder();
            sb.append("Total competitors: ").append(list.size()).append("\n");

            Competitor top = list.getTopPerformer();
            if (top != null) {
                sb.append("Top performer: ")
                  .append(top.getName().getFullName())
                  .append(" (ID ").append(top.getCompetitorId()).append(")")
                  .append(" Overall=").append(String.format("%.2f", top.getOverallScore()))
                  .append("\n");
            }

            int[] freq = list.getScoreFrequencies01();
            sb.append("Score frequency (all Score1..Score5 values across all competitors):\n");
            sb.append("Score:      0   1\n");
            sb.append("Frequency:  ").append(freq[0]).append("   ").append(freq[1]).append("\n");

            txtSummary.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Report error: " + ex.getMessage());
        }
    }

    private String prettyLevel(Level level) {
        // Displays Beginner instead of BEGINNER
        String s = level.name().toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
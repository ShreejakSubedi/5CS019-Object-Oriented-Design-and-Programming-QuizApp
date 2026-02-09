package quizapp.service;

import quizapp.model.Competitor;
import quizapp.model.CompetitorList;

public class ReportService {

    public String buildFinalReport() throws Exception {
        CompetitorList list = new CompetitorList();
        list.loadFromDatabase();

        StringBuilder sb = new StringBuilder();

        sb.append("Competitor Table:\n");
        sb.append(String.format("%-5s %-22s %-13s %-15s %-8s%n",
                "ID", "Name", "Level", "Scores", "Overall"));

        for (Competitor c : list.getAll()) {
            int[] s = c.getScoreArray();
            String scores = s[0]+" "+s[1]+" "+s[2]+" "+s[3]+" "+s[4];

            sb.append(String.format("%-5d %-22s %-13s %-15s %-8.2f%n",
                    c.getCompetitorId(),
                    c.getName().getFullName(),
                    c.getLevel().name(),
                    scores,
                    c.getOverallScore()
            ));
        }

        sb.append("\n");

        sb.append("Statistical Summary:\n");
        sb.append("• Total number of competitors: ").append(list.size()).append("\n");

        Competitor top = list.getTopPerformer();
        if (top != null) {
            sb.append("• Competitor with the highest score: ")
              .append(top.getName().getFullName())
              .append(" with an overall score of ")
              .append(String.format("%.2f", top.getOverallScore()))
              .append("\n");
        }

        int[] freq = list.getScoreFrequencies01();
        sb.append("• Frequency of individual scores:\n");
        sb.append("Score:      0   1\n");
        sb.append("Frequency:  ").append(freq[0]).append("   ").append(freq[1]).append("\n");

        return sb.toString();
    }
}
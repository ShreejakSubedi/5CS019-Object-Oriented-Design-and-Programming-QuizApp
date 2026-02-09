package quizapp.model;

import quizapp.dao.CompetitorDAO;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Competitor {
    private int competitorId;
    private Name name;
    private Level level;
    private int age;              // extra attribute (your choice)
    private int[] scores;         // length 5, each 0/1

    public Competitor(int competitorId, Name name, Level level, int age, int[] scores) {
        this.competitorId = competitorId;
        this.name = name;
        this.level = level;
        this.age = age;
        setScores(scores);
    }

    public int getCompetitorId() { return competitorId; }
    public void setCompetitorId(int competitorId) { this.competitorId = competitorId; }

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // PDF required method name:
    public int[] getScoreArray() {
        return Arrays.copyOf(scores, scores.length);
    }

    public void setScores(int[] scores) {
        if (scores == null || scores.length != 5) {
            throw new IllegalArgumentException("Scores array must have exactly 5 values.");
        }
        for (int s : scores) {
            if (s != 0 && s != 1) {
                throw new IllegalArgumentException("Each score must be 0 or 1.");
            }
        }
        this.scores = Arrays.copyOf(scores, scores.length);
    }

    // PDF required method name:
    public double getOverallScore() {
        int n;

        switch (level) {
            case BEGINNER:
                n = 3;
                break;
            case INTERMEDIATE:
                n = 4;
                break;
            case ADVANCED:
                n = 5;
                break;
            default:
                n = 5;
        }

        int[] copy = java.util.Arrays.copyOf(scores, scores.length);
        java.util.Arrays.sort(copy);

        int sumTop = 0;
        for (int i = copy.length - 1; i >= copy.length - n; i--) {
            sumTop += copy[i];
        }

        double avgTop = (double) sumTop / n;
        return avgTop * 5.0;
    }
    // PDF required method name:
    public String getFullDetails() {
        return "Competitor number " + competitorId + ", name " + name.getFullName() + ", age " + age + ".\n"
             + name.getFirstName() + " is " + level + " and received these scores: "
             + scores[0] + ", " + scores[1] + ", " + scores[2] + ", " + scores[3] + ", " + scores[4] + ".\n"
             + "This gives an overall score of " + formatScore(getOverallScore()) + ".";
    }

    // PDF required method name and format:
    public String getShortDetails() {
        return "CN " + competitorId + " (" + name.getInitials() + ") has overall score " + formatScore(getOverallScore()) + ".";
    }

    private String formatScore(double score) {
        DecimalFormat df = new DecimalFormat("0.##"); // 5 instead of 5.0
        return df.format(score);
    }
    
 // --- Database helper methods (PDF alignment) ---

    public void insertToDatabase() throws Exception {
        CompetitorDAO dao = new CompetitorDAO();
        dao.createCompetitor(this);
    }

    public void updateScoresInDatabase() throws Exception {
        CompetitorDAO dao = new CompetitorDAO();
        dao.updateScores(this.competitorId, this.getScoreArray());
    }

    public static Competitor loadFromDatabase(int competitorId) throws Exception {
        CompetitorDAO dao = new CompetitorDAO();
        return dao.getCompetitorById(competitorId);
    }
}


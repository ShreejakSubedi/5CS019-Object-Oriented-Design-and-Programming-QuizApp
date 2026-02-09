package quizapp.model;

import quizapp.dao.CompetitorDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of competitors loaded from the database.
 * Provides helper operations used in reporting such as finding
 * a top performer and calculating score frequencies.
 *
 * <p>This class supports Part 3 of the coursework: loading competitor
 * data, producing summary statistics and helping generate a report.</p>
 */
public class CompetitorList {

    private final List<Competitor> competitors = new ArrayList<>();

    /**
     * Loads all competitors from the database, replacing the existing list.
     *
     * @throws Exception if a database error occurs
     */
    public void loadFromDatabase() throws Exception {
        CompetitorDAO dao = new CompetitorDAO();
        competitors.clear();
        competitors.addAll(dao.getAllCompetitors());
    }

    /**
     * Returns a copy of the internal competitor list.
     *
     * @return list of competitors
     */
    public List<Competitor> getAll() {
        return new ArrayList<>(competitors);
    }

    /**
     * Finds the competitor with the highest overall score.
     *
     * @return top competitor, or null if list is empty
     */
    public Competitor getTopPerformer() {
        if (competitors.isEmpty()) return null;

        Competitor top = competitors.get(0);
        for (Competitor c : competitors) {
            if (c.getOverallScore() > top.getOverallScore()) {
                top = c;
            }
        }
        return top;
    }

    /**
     * Calculates frequency of individual scores across all competitors.
     * Since quiz scores are 0/1, this returns an int[2]:
     * index 0 = count of zeros, index 1 = count of ones.
     *
     * @return frequency array of size 2
     */
    public int[] getScoreFrequencies01() {
        int[] freq = new int[2]; // 0 and 1

        for (Competitor c : competitors) {
            int[] s = c.getScoreArray();
            for (int v : s) {
                if (v == 0) freq[0]++;
                else if (v == 1) freq[1]++;
            }
        }
        return freq;
    }

    /**
     * Returns the number of competitors in the list.
     *
     * @return competitor count
     */
    public int size() {
        return competitors.size();
    }
}
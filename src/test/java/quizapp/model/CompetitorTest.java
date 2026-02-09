package quizapp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompetitorTest {

    @Test
    void overallScore_beginner_best3_scaledTo5() {
        Name name = new Name("Test", "", "User");
        int[] scores = {1, 0, 1, 0, 1}; // best 3 = 1,1,1 => avg=1 => 5
        Competitor c = new Competitor(501, name, Level.BEGINNER, 21, scores);

        assertEquals(5.0, c.getOverallScore(), 0.0001);
    }

    @Test
    void overallScore_intermediate_best4_scaledTo5() {
        Name name = new Name("Test", "", "User");
        int[] scores = {1, 0, 1, 0, 0}; // best 4 = 1,1,0,0 => avg=0.5 => 2.5
        Competitor c = new Competitor(502, name, Level.INTERMEDIATE, 21, scores);

        assertEquals(2.5, c.getOverallScore(), 0.0001);
    }

    @Test
    void overallScore_advanced_best5_scaledTo5() {
        Name name = new Name("Test", "", "User");
        int[] scores = {1, 0, 0, 0, 0}; // avg=0.2 => 1.0
        Competitor c = new Competitor(503, name, Level.ADVANCED, 21, scores);

        assertEquals(1.0, c.getOverallScore(), 0.0001);
    }

    @Test
    void shortDetails_containsRequiredFormat() {
        Name name = new Name("Shreejak", "", "Subedi");
        int[] scores = {1, 1, 1, 1, 1};
        Competitor c = new Competitor(501, name, Level.BEGINNER, 21, scores);

        String s = c.getShortDetails();

        // Must start like: CN 501 (SS) ...
        assertTrue(s.startsWith("CN 501 (SS) has overall score"));
        assertTrue(s.endsWith(".")); // PDF example ends with a dot
    }
}
package quizapp.dao;

import quizapp.model.Competitor;
import quizapp.model.Level;
import quizapp.model.Name;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompetitorDAO {

    public void createCompetitor(Competitor c) throws Exception {
        String sql =
                "INSERT INTO Competitors(competitorId, firstName, middleName, lastName, level, age, score1, score2, score3, score4, score5) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int[] s = c.getScoreArray();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getCompetitorId());
            ps.setString(2, c.getName().getFirstName());
            ps.setString(3, c.getName().getMiddleName());
            ps.setString(4, c.getName().getLastName());
            ps.setString(5, c.getLevel().name());
            ps.setInt(6, c.getAge());
            ps.setInt(7, s[0]);
            ps.setInt(8, s[1]);
            ps.setInt(9, s[2]);
            ps.setInt(10, s[3]);
            ps.setInt(11, s[4]);

            ps.executeUpdate();
        }
    }

    public Competitor getCompetitorById(int competitorId) throws Exception {
        String sql = "SELECT * FROM Competitors WHERE competitorId = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, competitorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Name n = new Name(rs.getString("firstName"),
                                  rs.getString("middleName"),
                                  rs.getString("lastName"));

                Level level = Level.valueOf(rs.getString("level"));

                int[] scores = new int[] {
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("score3"),
                        rs.getInt("score4"),
                        rs.getInt("score5")
                };

                return new Competitor(rs.getInt("competitorId"), n, level, rs.getInt("age"), scores);
            }
        }
    }

    public void updateScores(int competitorId, int[] scores) throws Exception {
        String sql = "UPDATE Competitors SET score1=?, score2=?, score3=?, score4=?, score5=? WHERE competitorId=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, scores[0]);
            ps.setInt(2, scores[1]);
            ps.setInt(3, scores[2]);
            ps.setInt(4, scores[3]);
            ps.setInt(5, scores[4]);
            ps.setInt(6, competitorId);

            ps.executeUpdate();
        }
    }

    public List<Competitor> getAllCompetitors() throws Exception {
        String sql = "SELECT * FROM Competitors";
        List<Competitor> list = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Name n = new Name(rs.getString("firstName"),
                                  rs.getString("middleName"),
                                  rs.getString("lastName"));

                Level level = Level.valueOf(rs.getString("level"));

                int[] scores = new int[] {
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("score3"),
                        rs.getInt("score4"),
                        rs.getInt("score5")
                };

                list.add(new Competitor(rs.getInt("competitorId"), n, level, rs.getInt("age"), scores));
            }
        }

        return list;
    }
}
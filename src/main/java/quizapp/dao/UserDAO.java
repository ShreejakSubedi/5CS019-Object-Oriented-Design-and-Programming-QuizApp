package quizapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public void createUser(String username, String passwordHash, int competitorId) throws Exception {
        String sql = "INSERT INTO Users(username, passwordHash, competitorId) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setInt(3, competitorId);
            ps.executeUpdate();
        }
    }

    public UserRow findByUsername(String username) throws Exception {
        String sql = "SELECT username, passwordHash, competitorId FROM Users WHERE username = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new UserRow(
                        rs.getString("username"),
                        rs.getString("passwordHash"),
                        rs.getInt("competitorId")
                );
            }
        }
    }

    // Classic inner DTO class (instead of record)
    public static class UserRow {
        private final String username;
        private final String passwordHash;
        private final int competitorId;

        public UserRow(String username, String passwordHash, int competitorId) {
            this.username = username;
            this.passwordHash = passwordHash;
            this.competitorId = competitorId;
        }

        public String getUsername() { return username; }
        public String getPasswordHash() { return passwordHash; }
        public int getCompetitorId() { return competitorId; }
    }
}
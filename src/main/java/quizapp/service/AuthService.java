package quizapp.service;

import quizapp.dao.UserDAO;
import quizapp.dao.UserDAO.UserRow;
import quizapp.util.PasswordUtil;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public int login(String username, String password) throws Exception {

        UserRow row = userDAO.findByUsername(username);
        if (row == null) return -1;

        String hash = PasswordUtil.sha256(password);
        if (!hash.equals(row.getPasswordHash())) return -1;

        return row.getCompetitorId();
    }
}
package quizapp.service;

import quizapp.dao.CompetitorDAO;
import quizapp.model.Competitor;
import quizapp.model.CompetitorList;

public class Manager {

    private final CompetitorDAO competitorDAO = new CompetitorDAO();

    public CompetitorList loadCompetitors() throws Exception {
        CompetitorList list = new CompetitorList();
        list.loadFromDatabase();
        return list;
    }

    public Competitor getCompetitor(int competitorId) throws Exception {
        return competitorDAO.getCompetitorById(competitorId);
    }

    public void saveScores(int competitorId, int[] scores) throws Exception {
        competitorDAO.updateScores(competitorId, scores);
    }
}
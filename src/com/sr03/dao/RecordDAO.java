package com.sr03.dao;

import com.sr03.entities.RecordEntity;
import com.sr03.entities.UserAnswerEntity;

import java.sql.*;
import java.util.ArrayList;

import static com.sr03.dao.DAOUtility.*;

public class RecordDAO extends DAO<RecordEntity> {
    private static final String SQL_SELECT_ALL_USER_RECORDS = "SELECT * FROM records WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_RECORD_ANSWERS = "SELECT * FROM users_answers WHERE record_id = ?";
    private static final String SQL_INSERT = "INSERT INTO records (quiz_id, user_id, score, started_at, finished_at) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE records SET quiz_id = ?, user_id = ?, score = ?, started_at = ?, finished_at = ? WHERE id = ?";

    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private UserAnswerDAO userAnswerDAO;

    RecordDAO(DAOFactory daoFactory) {
        super(daoFactory, "records");
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.questionDAO = DAOFactory.getInstance().getQuestionDAO();
        this.userAnswerDAO = DAOFactory.getInstance().getUserAnswerDAO();
    }

    @Override
    public RecordEntity map(ResultSet resultSet) {
        RecordEntity record = new RecordEntity();
        try {
            record.setId(resultSet.getLong("id"));
            record.setQuiz_id(resultSet.getLong("quiz_id"));
            record.setUser_id(resultSet.getLong("user_id"));
            record.setScore(resultSet.getInt("score"));
            record.setStarted_at(resultSet.getTimestamp("started_at"));
            record.setFinished_at(resultSet.getTimestamp("finished_at"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public ArrayList<RecordEntity> getAllUserRecords(Long userId) {
        ArrayList<RecordEntity> records = getManyQuery(SQL_SELECT_ALL_USER_RECORDS, userId);

        for (RecordEntity record : records) {
            ArrayList<UserAnswerEntity> answers = userAnswerDAO.getManyQuery(SQL_SELECT_ALL_RECORD_ANSWERS, record.getId());
            record.setQuiz(quizDAO.get(record.getQuiz_id()));
            for (UserAnswerEntity answer : answers) {
                answer.setQuestion(questionDAO.get(answer.getQuestion_id()));
            }
            record.setAnswers(answers);
        }

        return records;
    }

    @Override
    public void create(RecordEntity record) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet autoGeneratedValues = null;

        try {
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_INSERT, true,
                    record.getQuiz_id(),
                    record.getUser_id(),
                    record.getScore(),
                    record.getStarted_at(),
                    record.getFinished_at()
            );

            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Échec de la création de la réponse au questionnaire.");
            }

            autoGeneratedValues = preparedStatement.getGeneratedKeys();
            if (autoGeneratedValues.next()) {
                record.setId(autoGeneratedValues.getLong(1));
            } else {
                throw new DAOException("Échec de la création de la réponse au questionnaire.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            silentClosures(autoGeneratedValues, preparedStatement, conn);
        }
    }

    @Override
    public void update(RecordEntity record) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_UPDATE, false,
                    record.getQuiz_id(),
                    record.getUser_id(),
                    record.getScore(),
                    record.getStarted_at(),
                    record.getFinished_at(),
                    record.getId()
            );
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DAOException("Échec de la modification de la réponse au questionnaire.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }
}

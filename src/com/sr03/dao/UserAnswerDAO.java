package com.sr03.dao;

import com.sr03.entities.UserAnswerEntity;

import java.sql.*;

import static com.sr03.dao.DAOUtility.*;

public class UserAnswerDAO extends DAO<UserAnswerEntity> {
    private static final String SQL_INSERT = "INSERT INTO users_answers (user_id, record_id, question_id, answer_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE users_answers SET user_id = ?, record_id = ?, question_id = ?, answer_id = ? WHERE id = ?";

    UserAnswerDAO(DAOFactory daoFactory) {
        super(daoFactory, "users_answers");
    }

    @Override
    public UserAnswerEntity map(ResultSet resultSet) {
        UserAnswerEntity userAnswer = new UserAnswerEntity();
        try {
            userAnswer.setUser_id(resultSet.getLong("user_id"));
            userAnswer.setRecord_id(resultSet.getLong("record_id"));
            userAnswer.setQuestion_id(resultSet.getLong("question_id"));
            userAnswer.setAnswer_id(resultSet.getLong("answer_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAnswer;
    }

    @Override
    public UserAnswerEntity get(Long id) {
        UserAnswerEntity userAnswer = super.get(id);
        return userAnswer;
    }

    @Override
    public void create(UserAnswerEntity userAnswer) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_INSERT, false,
                    userAnswer.getUser_id(),
                    userAnswer.getRecord_id(),
                    userAnswer.getQuestion_id(),
                    userAnswer.getAnswer_id()
            );

            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Échec de la création de la réponse de l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }

    @Override
    public void update(UserAnswerEntity userAnswer) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_UPDATE, false,
                    userAnswer.getUser_id(),
                    userAnswer.getRecord_id(),
                    userAnswer.getQuestion_id(),
                    userAnswer.getAnswer_id()
            );
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DAOException("Échec de la modification de la réponse de l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }
}

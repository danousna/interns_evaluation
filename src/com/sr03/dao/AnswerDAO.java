package com.sr03.dao;

import com.sr03.entities.AnswerEntity;

import java.sql.*;

import static com.sr03.dao.DAOUtility.*;

public class AnswerDAO extends DAO<AnswerEntity> {
    private static final String SQL_INSERT = "INSERT INTO answers (body, is_active, is_correct, `order`, question_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE answers SET body = ?, is_active = ?, is_correct = ?, `order` = ?, question_id = ? WHERE id = ?";

    AnswerDAO(DAOFactory daoFactory) {
        super(daoFactory, "answers");
    }

    @Override
    public AnswerEntity map(ResultSet resultSet) {
        AnswerEntity answer = new AnswerEntity();
        try {
            answer.setId(resultSet.getLong("id"));
            answer.setBody(resultSet.getString("body"));
            answer.setIs_active(resultSet.getBoolean("is_active"));
            answer.setIs_correct(resultSet.getBoolean("is_correct"));
            answer.setOrder(resultSet.getLong("order"));
            answer.setQuestion_id(resultSet.getLong("question_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    public void create(AnswerEntity answer) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet autoGeneratedValues = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_INSERT, true,
                    answer.getBody(),
                    answer.getIs_active(),
                    answer.getIs_correct(),
                    answer.getOrder(),
                    answer.getQuestion_id()
            );

            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Échec de la création de la réponse.");
            }

            autoGeneratedValues = preparedStatement.getGeneratedKeys();
            if (autoGeneratedValues.next()) {
                answer.setId(autoGeneratedValues.getLong(1));
            } else {
                throw new DAOException("Échec de la création de la réponse.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(autoGeneratedValues, preparedStatement, conn);
        }
    }

    @Override
    public void update(AnswerEntity answer) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_UPDATE, false,
                    answer.getBody(),
                    answer.getIs_active(),
                    answer.getIs_correct(),
                    answer.getOrder(),
                    answer.getQuestion_id(),
                    answer.getId()
            );
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DAOException("Échec de la modification de la réponse.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }
}

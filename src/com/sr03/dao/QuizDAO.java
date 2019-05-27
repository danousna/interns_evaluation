package com.sr03.dao;

import com.sr03.entities.QuizEntity;
import com.sr03.entities.SubjectEntity;

import java.sql.*;
import java.util.ArrayList;

import static com.sr03.dao.DAOUtility.*;

public class QuizDAO extends DAO<QuizEntity> {
    private static final String SQL_INSERT = "INSERT INTO quizzes (name, is_active, subject_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE quizzes SET name = ?, is_active = ?, subject_id = ? WHERE id = ?";
    private static final String SQL_CHANGE_AVAILABILITY = "UPDATE quizzes SET is_active = (is_active + 1)%2 WHERE id = ?";

    private SubjectDAO subjectDAO;

    QuizDAO(DAOFactory daoFactory) {
        super(daoFactory, "quizzes");
        this.subjectDAO = new SubjectDAO(daoFactory);
    }

    @Override
    public QuizEntity map(ResultSet resultSet) {
        QuizEntity quiz = new QuizEntity();
        try {
            quiz.setId(resultSet.getLong("id"));
            quiz.setName(resultSet.getString("name"));
            quiz.setSubject_id(resultSet.getLong("subject_id"));
            quiz.setIs_active(resultSet.getBoolean("is_active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    @Override
    public QuizEntity get(Long id) {
        QuizEntity quiz = super.get(id);
        quiz.setSubject(subjectDAO.get(quiz.getSubject_id()));
        return quiz;
    }

    @Override
    public ArrayList<QuizEntity> getAll() {
        ArrayList<QuizEntity> quizzes = super.getAll();
        for (QuizEntity quiz : quizzes) {
            quiz.setSubject(subjectDAO.get(quiz.getSubject_id()));
        }
        return quizzes;
    }

    @Override
    public void create(QuizEntity quiz) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet autoGeneratedValues = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_INSERT, true,
                    quiz.getName(),
                    quiz.getIs_active(),
                    quiz.getSubject_id()
            );

            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Échec de la création du questionnaire.");
            }

            autoGeneratedValues = preparedStatement.getGeneratedKeys();
            if (autoGeneratedValues.next()) {
                quiz.setId(autoGeneratedValues.getLong(1));
            } else {
                throw new DAOException("Échec de la création du questionnaire.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(autoGeneratedValues, preparedStatement, conn);
        }
    }

    @Override
    public void update(QuizEntity quiz) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_UPDATE, false,
                    quiz.getName(),
                    quiz.getIs_active(),
                    quiz.getSubject_id(),
                    quiz.getId()
            );
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DAOException("Échec de la modification du sujet.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }

    public void changeQuizAvailability(long id) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, SQL_CHANGE_AVAILABILITY, false, id);
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DAOException("Echec du changement de disponibilité du sujet.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }
}

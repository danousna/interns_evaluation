package com.sr03.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;

import static com.sr03.dao.DAOUtility.initPreparedStatement;
import static com.sr03.dao.DAOUtility.silentClosures;

abstract class DAO<T> {
    private static final String SQL_SELECT_ALL = "SELECT * FROM {0}";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM {0} WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM {0} WHERE id = ?";

    DAOFactory daoFactory;
    private String database;

    DAO(DAOFactory daoFactory, String database) {
        this.daoFactory = daoFactory;
        this.database = database;
    }

    protected abstract T map(ResultSet resultSet);

    T getQuery(String query, Object value) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T entity = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, query, false, value);
            resultSet = preparedStatement.executeQuery();

            /* Parcours de la ligne de données de l'eventuel ResultSet retourné */
            if (resultSet.next()) {
                entity = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, conn);
        }

        return entity;
    }

    public ArrayList<T> getManyQuery(String query) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<T> entities = new ArrayList<>();

        try {
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, query, false);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entities.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, conn);
        }

        return entities;
    }

    ArrayList<T> getManyQuery(String query, Object value) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<T> entities = new ArrayList<>();

        try {
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, query, false, value);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entities.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, conn);
        }

        return entities;
    }

    public abstract void create(T e);

    public T get(Long id) {
        return getQuery(MessageFormat.format(SQL_SELECT_BY_ID, database), id);
    }

    public ArrayList<T> getAll() {
        return getManyQuery(MessageFormat.format(SQL_SELECT_ALL, database));
    }

    public abstract void update(T e);

    public void delete(Long id) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(conn, MessageFormat.format(SQL_DELETE, database), false, id);
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DAOException("Echec lors de la suppression");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentClosures(preparedStatement, conn);
        }
    }
}

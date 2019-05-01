package com.sr03.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtility {
    /*
     * Initialise la requête préparée basée sur la conn passée en argument,
     * avec la requête SQL et les objets donnés.
     */
    public static PreparedStatement initPreparedStatement(Connection conn, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }

    /* Fermeture silencieuse du resultset */
    public static void silentClose(ResultSet resultSet) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse du statement */
    public static void silentClose(Statement statement) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse de la conn */
    public static void silentClose(Connection conn) {
        if ( conn != null ) {
            try {
                conn.close();
            } catch ( SQLException e ) {
                System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void silentClosures(Statement statement, Connection conn) {
        silentClose(statement);
        silentClose(conn);
    }

    /* Fermetures silencieuses du resultset, du statement et de la connexion */
    public static void silentClosures(ResultSet resultSet, Statement statement, Connection conn) {
        silentClose(resultSet);
        silentClose(statement);
        silentClose(conn);
    }
}


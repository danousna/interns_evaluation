package com.sr03.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private static final String PROP_FILE       = "/com/sr03/config/env.properties";
    private static final String PROP_URL        = "db.url";
    private static final String PROP_DRIVER     = "db.driver";
    private static final String PROP_USER       = "db.user";
    private static final String PROP_PASSWORD   = "db.password";

    private String url;
    private String username;
    private String password;

    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String user;
        String password;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propFile = classLoader.getResourceAsStream( PROP_FILE );

        if ( propFile == null ) {
            throw new DAOConfigurationException( "Le fichier properties " + PROP_FILE + " est introuvable." );
        }

        try {
            properties.load(propFile);
            url = properties.getProperty(PROP_URL);
            driver = properties.getProperty(PROP_DRIVER);
            user = properties.getProperty(PROP_USER);
            password = properties.getProperty(PROP_PASSWORD);
        } catch (IOException e) {
            throw new DAOConfigurationException("Impossible de charger le fichier properties " + PROP_FILE, e);
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
        }

        return new DAOFactory(url, user, password);
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO
     */
    public UserDAO getUserDAO() { return new UserDAO(this); }

    public SubjectDAO getSubjetDAO() {
        return new SubjectDAO(this);
    }

    public QuizDAO getQuizDAO() {
        return new QuizDAO(this);
    }

    public QuestionDAO getQuestionDAO() {
        return new QuestionDAO(this);
    }

    public AnswerDAO getAnswerDAO() {
        return new AnswerDAO(this);
    }

    public RecordDAO getRecordDAO() { return new RecordDAO(this); }

    public UserAnswerDAO getUserAnswerDAO() { return new UserAnswerDAO(this); }
}

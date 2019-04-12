package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import src.dao.DAOUtility.*;

public class UserDaoImpl implements UserDao {
    private DAOFactory daoFactory;
    private static final String SQL_SELECT_BY_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";

    UserDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public models.UserModel find(String email) throws DAOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        models.UserModel userModel = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            preparedStatement = initPrepared
        }
    }

    @Override
    public void create(models.UserModel user) throws IllegalArgumentException, DAOException {

    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean User.
     */
    private static models.UserModel map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId( resultSet.getLong( "id" ) );
        user.setEmail( resultSet.getString( "email" ) );
        user.setMotDePasse( resultSet.getString( "mot_de_passe" ) );
        user.setNom( resultSet.getString( "nom" ) );
        user.setDateInscription( resultSet.getTimestamp( "date_inscription" ) );
        return user;
    }
}

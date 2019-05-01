package com.sr03.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.sr03.beans.User;
import com.sr03.dao.DAOException;
import com.sr03.dao.UserDao;

public final class Register {
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PASS = "password";
    private static final String FIELD_CONF = "confirmation";
    private static final String FIELD_NAME = "name";

    private static final String ENCRYPTION_METHOD = "SHA-256";

    private String result;
    private Map<String, String> errors = new HashMap<String, String>();
    private UserDao userDao;

    public Register( UserDao userDao ) {
        this.userDao = userDao;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    public User registerUser( HttpServletRequest request ) {
        String email = getValeurChamp( request, FIELD_EMAIL );
        String password = getValeurChamp( request, FIELD_PASS );
        String confirmation = getValeurChamp( request, FIELD_CONF );
        String nom = getValeurChamp( request, FIELD_NAME );

        User user = new User();
        try {
            processEmail( email, user );
            processPassword( password, confirmation, user );
            processName( nom, user );

            if ( errors.isEmpty() ) {
                userDao.creer( user );
                result = "Succès de l'inscription.";
            } else {
                result = "Échec de l'inscription.";
            }
        } catch ( DAOException e ) {
            result = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return user;
    }

    /*
     * Appel à la validation de l'adresse email reçue et initialisation de la
     * propriété email du bean
     */
    private void processEmail( String email, User user ) {
        try {
            validateEmail( email );
        } catch ( FormValidationException e ) {
            setError( FIELD_EMAIL, e.getMessage() );
        }
        user.setEmail( email );
    }

    /*
     * Appel à la validation des mots de passe reçus, chiffrement du mot de
     * passe et initialisation de la propriété password du bean
     */
    private void processPassword( String password, String confirmation, User user ) {
        try {
            validatePassword( password, confirmation );
        } catch ( FormValidationException e ) {
            setError( FIELD_PASS, e.getMessage() );
            setError( FIELD_CONF, null );
        }

        /*
         * Utilisation de la lib Jasypt pour chiffrer le mot de passe.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ENCRYPTION_METHOD );
        passwordEncryptor.setPlainDigest( false );
        String encryptedPassword = passwordEncryptor.encryptPassword( password );

        user.setMotDePasse( encryptedPassword );
    }

    /*
     * Appel à la validation du nom reçu et initialisation de la propriété nom
     * du bean
     */
    private void processName( String nom, User user ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setError( FIELD_NAME, e.getMessage() );
        }
        user.setNom( nom );
    }

    /* Validation de l'adresse email */
    private void validateEmail( String email ) throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse email valide." );
            } else if ( userDao.find( email ) != null ) {
                throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir une adresse email." );
        }
    }

    /* Validation des mots de passe */
    private void validatePassword( String password, String confirmation ) throws FormValidationException {
        if ( password != null && confirmation != null ) {
            if ( !password.equals( confirmation ) ) {
                throw new FormValidationException( "Les mots de passe entrÃ©s sont différents, merci de les saisir à nouveau." );
            } else if ( password.length() < 3 ) {
                throw new FormValidationException( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
        }
    }

    /* Validation du nom */
    private void validationNom( String nom ) throws FormValidationException {
        if ( nom != null && nom.length() < 3 ) {
            throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setError( String champ, String message ) {
        errors.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
package com.sr03.forms;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sr03.dao.UserDAO;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.sr03.beans.User;
import com.sr03.dao.DAOException;

public final class RegisterForm {
    private static final String FIELD_NAME = "name";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PASS = "password";
    private static final String FIELD_PASS_CONF = "password_confirmation";
    private static final String FIELD_COMPANY = "company";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IS_ADMIN = "is_admin";

    private static final String ENCRYPTION_METHOD = "SHA-256";

    private String result;
    private Map<String, String> errors = new HashMap<String, String>();
    private UserDAO userDao;

    public RegisterForm(UserDAO userDao) {
        this.userDao = userDao;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    public User registerUser( HttpServletRequest request ) {
        String email = getFieldValue( request, FIELD_EMAIL );
        String password = getFieldValue( request, FIELD_PASS );
        String password_confirmation = getFieldValue( request, FIELD_PASS_CONF );

        User user = new User();

        try {
            user.setName(getFieldValue( request, FIELD_NAME ));
            user.setCompany(getFieldValue( request, FIELD_COMPANY ));
            user.setPhone(getFieldValue( request, FIELD_PHONE ));
            user.setIs_admin(Boolean.valueOf(getFieldValue( request, FIELD_IS_ADMIN )));

            processEmail(email, user);
            processPassword(password, password_confirmation, user);

            if (errors.isEmpty()) {
                Date date = new Date();
                user.setCreated_at(new Timestamp(date.getTime()));
                user.setIs_admin(true);

                userDao.create(user);

                result = "Succès de l'inscription.";
            } else {
                result = "Échec de l'inscription.";
            }
        } catch (DAOException e) {
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
    private void processPassword( String password, String password_confirmation, User user ) {
        try {
            validatePassword( password, password_confirmation );
        } catch ( FormValidationException e ) {
            setError( FIELD_PASS, e.getMessage() );
            setError( FIELD_PASS_CONF, null );
        }

        /*
         * Utilisation de la lib Jasypt pour chiffrer le mot de passe.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ENCRYPTION_METHOD );
        passwordEncryptor.setPlainDigest( false );
        String encryptedPassword = passwordEncryptor.encryptPassword( password );

        user.setPassword( encryptedPassword );
    }

    /* Validation de l'adresse email */
    private void validateEmail( String email ) throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse email valide." );
            }
            // else if ( userDao.get(email) != null ) {
                // throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
            // }
        } else {
            throw new FormValidationException( "Merci de saisir une adresse email." );
        }
    }

    /* Validation des mots de passe */
    private void validatePassword( String password, String password_confirmation ) throws FormValidationException {
        if ( password != null && password_confirmation != null ) {
            if ( !password.equals( password_confirmation ) ) {
                throw new FormValidationException( "Les mots de passe entrÃ©s sont différents, merci de les saisir à nouveau." );
            } else if ( password.length() < 3 ) {
                throw new FormValidationException( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setError( String field, String message ) {
        errors.put( field, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getFieldValue( HttpServletRequest request, String field ) {
        String value = request.getParameter( field );
        if ( value == null || value.trim().length() == 0 ) {
            return null;
        } else {
            return value;
        }
    }
}
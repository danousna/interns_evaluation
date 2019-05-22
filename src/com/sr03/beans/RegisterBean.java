package com.sr03.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

@ManagedBean
@ViewScoped
public class RegisterBean extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    private UserDAO userDAO;

    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    public RegisterBean() {
        user = new User();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void init() {
        userDAO = DAOFactory.getInstance().getUserDao();
    }

    public void register()  {
        init();

        try {
            processEmail();
            processPassword();

            if (errors.isEmpty()) {
                Timestamp date = new Timestamp(System.currentTimeMillis());
                user.setCreated_at(date);
                user.setIs_admin(true);

                userDAO.create(user);

                result = "Succès de l'inscription.";
            } else {
                result = "Échec de l'inscription.";
            }
        } catch (DAOException e) {
            result = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(result) );
    }

    private void processEmail() {
        String email = user.getEmail();

        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                errors.put( "email", "Merci de saisir une adresse email valide." );
            }
            // else if ( userDao.get(email) != null ) {
            // throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
            // }
        } else {
            errors.put( "email", "Merci de saisir une adresse email." );
        }
    }

    private void processPassword() {
        String password = user.getPassword();

        if ( password == null) {
            errors.put( "password", "Merci de saisir un mot de passe." );
            return;
        }

        /*
         * Utilisation de la lib Jasypt pour chiffrer le mot de passe.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( false );
        String encryptedPassword = passwordEncryptor.encryptPassword( password );

        user.setPassword( encryptedPassword );
    }
}
package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class UsersBean extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    private List<User> users;
    private UserDAO userDAO;

    private Map<String, String> errors;

    public UsersBean() {
        this.userDAO = DAOFactory.getInstance().getUserDao();
        this.user = new User();
        this.users = userDAO.getAll();
    }

    public User getUser() {
        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> u) {
        users = u;
    }

    public void create()  {
        this.userDAO = DAOFactory.getInstance().getUserDao();
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            processEmail();
            processPassword();

            if (errors.isEmpty()) {
                Timestamp date = new Timestamp(System.currentTimeMillis());
                user.setCreated_at(date);
                user.setIs_admin(true);

                userDAO.create(user);

                context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Utilisateur enregistré.",
                    null
                ));
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de la création de l'utilisateur.",
                        null
                ));
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }
    }

    public void changeUserActivity(long id) {
        userDAO.changeUserActivity(id);
        User changedUser = userDAO.get(id);
        FacesMessage message = new FacesMessage( "User N°" + changedUser.getId() + " " + changedUser.getName() + " " + (changedUser.getIs_active() ? "ACTIVÉ" : "DÉSACTIVÉ"));
        FacesContext.getCurrentInstance().addMessage( null, message );
        this.users = userDAO.getAll();
    }

    private void processEmail() {
        String email = user.getEmail();

        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                errors.put( "email", "Merci de saisir une adresse email valide." );
            }
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

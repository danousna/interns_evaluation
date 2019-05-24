package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserCreateBean extends HttpServlet {
    private UserEntity user;
    private UserDAO userDAO;

    private List<String> errors = new ArrayList<>();

    public UserCreateBean() {
        this.userDAO = DAOFactory.getInstance().getUserDAO();
        this.user = new UserEntity();
    }

    public UserEntity getUser() {
        return user;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void create()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            processPassword();

            if (errors.isEmpty()) {
                Timestamp date = new Timestamp(System.currentTimeMillis());
                user.setCreated_at(date);
                user.setIs_active(true);

                userDAO.create(user);

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Utilisateur enregistré.",
                        null
                ));
                try {
                    context.getExternalContext().redirect("users.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public String changeUserActivity(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        userDAO.changeUserActivity(id);
        UserEntity changedUser = userDAO.get(id);
        FacesMessage message = new FacesMessage( "User N°" + changedUser.getId() + " " + changedUser.getName() + " " + (changedUser.getIs_active() ? "ACTIVÉ" : "DÉSACTIVÉ"));
        context.addMessage( null, message );

        try {
            context.getExternalContext().redirect("users.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    private void processPassword() {
        String password = user.getPassword();

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

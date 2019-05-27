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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserBean extends HttpServlet {
    private UserEntity user;
    private UserDAO userDAO;

    private Long editId;

    private List<String> errors = new ArrayList<>();

    public UserBean() {
        this.userDAO = DAOFactory.getInstance().getUserDAO();
        this.user = new UserEntity();
    }

    public void init() {
        if (editId != null) {
            user = userDAO.get(editId);
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) { this.user = user; }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Long getEditId() {
        return editId;
    }

    public void setEditId(Long editId) {
        this.editId = editId;
    }

    public String save()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (editId == null) {
                processPassword();
            }

            if (errors.isEmpty()) {
                if (editId == null) {
                    Timestamp date = new Timestamp(System.currentTimeMillis());
                    user.setCreated_at(date);
                    user.setIs_active(true);

                    userDAO.create(user);
                } else {
                    userDAO.update(user);
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Utilisateur enregistré.",
                        null
                ));

                return "users.xhtml?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de l'enregistrement de l'utilisateur.",
                        null
                ));
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'enregistrement : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }

        if (editId != null) {
            return "user_form?id=" + editId;
        } else {
            return "user_form";
        }
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

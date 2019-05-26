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
public class UserBean extends HttpServlet {
    private UserEntity user;
    private UserDAO userDAO;
    private Boolean modify = false;

    private List<String> errors = new ArrayList<>();

    public UserBean() {
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

    public Boolean getModify() {
        return modify;
    }

    public void setModify(Boolean modify) {
        this.modify = modify;
    }

    public String modifyUser(Long id) {
        user = userDAO.get(id);
        modify = true;

        return "user_form";
    }

    public void save()  {
        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println(modify);

        try {
            processPassword();

            if (errors.isEmpty()) {
                if (!modify) {
                    Timestamp date = new Timestamp(System.currentTimeMillis());
                    user.setCreated_at(date);
                    user.setIs_active(true);

                    userDAO.create(user);
                } else {
                    userDAO.update(user);
                }

                // We reset user object.
                user = new UserEntity();

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Utilisateur enregistré.",
                        null
                ));

                // Ici, on redirige au lieu de simplement changer de view.
                // Cela permet d'actualiser la liste des users.
                try {
                    context.getExternalContext().redirect("users.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

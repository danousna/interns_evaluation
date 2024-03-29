package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;
import com.sr03.utilities.mail.MailFactory;
import com.sr03.utilities.security.PasswordGenerator;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserBean extends HttpServlet {
    private UserEntity user;
    private UserDAO userDAO;

    private Long id;

    private List<String> errors = new ArrayList<>();

    public UserBean() {
        this.userDAO = DAOFactory.getInstance().getUserDAO();
        this.user = new UserEntity();
    }

    public void init() {
        if (id != null) {
            user = userDAO.get(id);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String save()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (errors.isEmpty()) {
                if (id == null) {
                    Timestamp date = new Timestamp(System.currentTimeMillis());
                    user.setCreated_at(date);
                    user.setIs_active(true);

                    String password = PasswordGenerator.get(10);
                    user.setPassword(encryptPassword(password));

                    userDAO.create(user);

                    String body = "<p>Bonjour {0} !</p><p>Votre compte a été crée par un administrateur. Voici vos identifiants :</p><p>Email : {1}</p><p>Mot de passe : {2}</p><p>Adios !</p>";
                    body = MessageFormat.format(body, user.getName(), user.getEmail(), password);

                    MailFactory.getInstance().send(
                            "admin@sr03-interns-evaluation.fr",
                            user.getEmail(),
                            "SR03 Interns Evaluation - Vos identifiants",
                            body
                    );
                } else {
                    userDAO.update(user);
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Utilisateur enregistré.",
                        null
                ));

                return "users.xhtml";
            } else {
                for (String error : errors) {
                    context.addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            error,
                            null
                    ));
                }
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'enregistrement : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }

        if (id != null) {
            return "user_form.xhtml?id=" + id;
        } else {
            return "user_form.xhtml";
        }
    }

    private String encryptPassword(String pwd) {
        /*
         * Utilisation de la lib Jasypt pour chiffrer le mot de passe.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( false );
        return passwordEncryptor.encryptPassword(pwd);
    }
}

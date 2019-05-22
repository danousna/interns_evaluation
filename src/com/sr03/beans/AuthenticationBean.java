package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.utilities.SessionUtils;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class AuthenticationBean implements Serializable {
    private static final long serialVersionUID = 1094801825228386363L;

    private UserDAO userDAO;

    private String username;
    private String password;
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String login() {
        userDAO = DAOFactory.getInstance().getUserDao();
        User user= userDAO.get(username);

        if (user != null) {
            ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
            passwordEncryptor.setAlgorithm( "SHA-256" );
            passwordEncryptor.setPlainDigest( false );

            if(passwordEncryptor.checkPassword(password, user.getPassword())) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", user.getName());
                return "authenticated";
            }
        } else {
            FacesMessage message = new FacesMessage( "Connexion impossible !");
            FacesContext.getCurrentInstance().addMessage( null, message );
        }

        return "login";
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}

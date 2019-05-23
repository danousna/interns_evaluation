package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class AuthenticationBean implements Serializable {
    private static final long serialVersionUID = 1094801825228386363L;

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

    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserDAO userDAO = DAOFactory.getInstance().getUserDao();
        User user= userDAO.get(username);

        if (user != null) {
            ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
            passwordEncryptor.setAlgorithm( "SHA-256" );
            passwordEncryptor.setPlainDigest( false );

            if(passwordEncryptor.checkPassword(password, user.getPassword())) {
                context.getExternalContext().getSessionMap().put("username", user.getName());
                try {
                    context.getExternalContext().redirect("index.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            context.addMessage(null, new FacesMessage("Connexion impossible."));
        }
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

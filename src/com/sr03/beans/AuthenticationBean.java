package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;
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

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        UserEntity user= userDAO.get(email);

        if (user != null) {
            if (!user.getIs_active()) {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Connexion impossible.",
                        "Compte désactivé, veuillez contacter un administrateur pour le réactiver."
                ));
                return;
            }

            ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
            passwordEncryptor.setAlgorithm( "SHA-256" );
            passwordEncryptor.setPlainDigest( false );

            if(passwordEncryptor.checkPassword(password, user.getPassword())) {
                context.getExternalContext().getSessionMap().put("email", user.getEmail());
                context.getExternalContext().getSessionMap().put("is_admin", user.getIs_admin());
                context.getExternalContext().getSessionMap().put("name", user.getName());

                try {
                    context.getExternalContext().redirect("index.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Connexion impossible.",
                    "Vérifiez vos identifiants de connexion"
            ));
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

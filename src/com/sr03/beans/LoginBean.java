package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.utilities.SessionUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1094801825228386363L;

    private UserDAO userDAO;

    private String username;
    private String password;
    private String message;

    @Override
    public void init() {
        userDAO = DAOFactory.getInstance().getUserDao();
    }

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

    public String validate() {
        boolean valid = userDAO.validate(username, password);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", username);
            return "admin";
        } else {
            FacesMessage message = new FacesMessage( "Connexion impossible !");
            FacesContext.getCurrentInstance().addMessage( null, message );
        }
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}

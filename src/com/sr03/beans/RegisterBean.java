package com.sr03.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;

@ManagedBean
@ViewScoped
public class RegisterBean extends HttpServlet implements Serializable {
        private static final long serialVersionUID = 1L;

    private User user;

    private UserDAO userDAO;

    // Initialisation de l'entité utilisateur
    public RegisterBean() {
        user = new User();
    }

    @Override
    public void init() {
        userDAO = DAOFactory.getInstance().getUserDao();
    }

    public void register()  {
        init();
        initDate();
        initIsActive();
        userDAO.create(user);
        FacesMessage message = new FacesMessage( "Succès de l'inscription !");
        FacesContext.getCurrentInstance().addMessage( null, message );
    }

    public User getUser() {
        return user;
    }

    private void initDate() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        user.setCreated_at(date);
    }

    private void initIsActive() {
        user.setIs_active(true);
    }
}
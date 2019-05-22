package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.beans.User;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.util.List;

@ManagedBean
@ViewScoped
public class ListUsersBean extends HttpServlet {
    private List<User> users;

    private UserDAO userDao;

    @Override
    @PostConstruct
    public void init() {
        this.userDao = DAOFactory.getInstance().getUserDao();
        this.users = userDao.getAll();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> u) {
        users = u;
    }

    public void changeUserActivity(long id) {
        userDao.changeUserActivity(id);
        User changedUser = userDao.get(id);
        FacesMessage message = new FacesMessage( "User N°" + changedUser.getId() + " " + changedUser.getName() + " " + (changedUser.getIs_active() ? "ACTIVÉ" : "DÉSACTIVÉ"));
        FacesContext.getCurrentInstance().addMessage( null, message );
        init();
    }
}

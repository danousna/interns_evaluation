package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServlet;
import java.util.List;

@ManagedBean
@ViewScoped
public class UsersBean extends HttpServlet {
    private List<User> users;
    private UserDAO userDAO;

    public UsersBean() {
        this.userDAO = DAOFactory.getInstance().getUserDao();
        this.users = userDAO.getAll();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> u) {
        users = u;
    }
}

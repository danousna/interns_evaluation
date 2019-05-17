package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.beans.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServlet;
import java.util.List;

@ManagedBean
@ViewScoped
public class ListUsersBean extends HttpServlet {
    public List<User> users;

    private UserDAO userDao;

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
}

package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServlet;
import java.util.List;

@ManagedBean
@ViewScoped
public class UsersBean extends HttpServlet {
    private List<UserEntity> users;
    private UserDAO userDAO;

    public UsersBean() {
        this.userDAO = DAOFactory.getInstance().getUserDAO();
        this.users = userDAO.getAll();
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> u) {
        users = u;
    }
}

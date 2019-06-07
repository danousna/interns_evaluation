package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.sql.Time;
import java.sql.Timestamp;
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

    public String changeUserActivity(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        userDAO.changeUserActivity(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la modification.",
                null
        ));

        return "users.xhtml";
    }

    public String delete(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        userDAO.delete(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));

        return "users.xhtml";
    }

    public Long getGlobalStats(Long id) {
       return userDAO.getGlobalStats(id);
    }

    public Timestamp getAVGTime(Long id) {
        return userDAO.getAVGTime(id);
    }

    public Timestamp getSumTime(Long id) {
        return userDAO.getSumTime(id);
    }
}

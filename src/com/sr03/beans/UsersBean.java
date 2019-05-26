package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

    public void changeUserActivity(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        int index = getIndex(id);

        userDAO.changeUserActivity(id);

        if (index != -1) {
            UserEntity user = users.get(index);
            user.setIs_active(!user.getIs_active());
            users.set(index, user);
        }

        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la modification.",
                null
        ));
    }

    public void deleteUser(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        userDAO.delete(id);
        users.remove(getIndex(id));
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));
    }

    private int getIndex(Long id) {
        int pos = 0;

        for (UserEntity user : users) {
            if (user.getId().equals(id)) {
                return pos;
            }
            pos++;
        }

        return -1;
    }
}

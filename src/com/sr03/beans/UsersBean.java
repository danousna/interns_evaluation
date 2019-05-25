package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.UserDAO;
import com.sr03.entities.UserEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
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

    public String changeUserActivity(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        userDAO.changeUserActivity(id);
        UserEntity changedUser = userDAO.get(id);
        FacesMessage message = new FacesMessage( "Utilisateur N°" + changedUser.getId() + " " + changedUser.getName() + " " + (changedUser.getIs_active() ? "ACTIVÉ" : "DÉSACTIVÉ"));
        context.addMessage( null, message );

//        try {
//            context.getExternalContext().redirect("users.xhtml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return "success";
    }

    public String deleteUser(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        userDAO.deleteUser(id);
        FacesMessage message = new FacesMessage( "Utilisateur SUPPRIMÉ");
        context.addMessage( null, message );

        try {
            context.getExternalContext().redirect("users.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
}

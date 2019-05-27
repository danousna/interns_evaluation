package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.SubjectDAO;
import com.sr03.entities.SubjectEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import java.util.List;

@ManagedBean
@ViewScoped
public class SubjectsBean extends HttpServlet {
    private List<SubjectEntity> subjects;
    private SubjectDAO subjectDAO;

    public SubjectsBean() {
        this.subjectDAO = DAOFactory.getInstance().getSubjetDAO();
        this.subjects = subjectDAO.getAll();
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectEntity> s) { subjects = s; }

    public String delete(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        subjectDAO.delete(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));

        try {
            context.getExternalContext().redirect("subjects.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "subjects.xhtml?faces-redirect=true";
    }
}

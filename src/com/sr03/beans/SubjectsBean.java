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

    public String deleteSubject(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        subjectDAO.deleteSubject(id);
        FacesMessage message = new FacesMessage( "Sujet SUPPRIMÉ");
        context.addMessage( null, message );

        try {
            context.getExternalContext().redirect("subjects.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
}

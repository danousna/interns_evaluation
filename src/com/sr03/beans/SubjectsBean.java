package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.SubjectDAO;
import com.sr03.entities.SubjectEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
}

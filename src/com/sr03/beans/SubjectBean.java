package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.SubjectDAO;
import com.sr03.entities.SubjectEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SubjectBean {
    private SubjectEntity subject;
    private SubjectDAO subjectDAO;

    private Long editId;

    private List<String> errors = new ArrayList<>();

    public SubjectBean() {
        this.subjectDAO = DAOFactory.getInstance().getSubjetDAO();
        this.subject = new SubjectEntity();
    }

    public void init() {
        if (editId != null) {
            subject = subjectDAO.get(editId);
        }
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Long getEditId() {
        return editId;
    }

    public void setEditId(Long editId) {
        this.editId = editId;
    }

    public String save()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (errors.isEmpty()) {
                if (editId == null) {
                    subjectDAO.create(subject);
                } else {
                    subjectDAO.update(subject);
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Sujet enregistré.",
                        null
                ));

                return "subjects.xhtml?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de l'enregistrement du sujet.",
                        null
                ));
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'enregistrement : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }

        if (editId != null) {
            return "subject_form?id=" + editId;
        } else {
            return "subject_form";
        }
    }
}

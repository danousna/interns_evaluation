package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.SubjectDAO;
import com.sr03.entities.SubjectEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SubjectBean {
    private SubjectEntity subject;
    private SubjectDAO subjectDAO;

    private Long id;

    private List<String> errors = new ArrayList<>();

    public SubjectBean() {
        this.subjectDAO = DAOFactory.getInstance().getSubjetDAO();
        this.subject = new SubjectEntity();
    }

    public void init() {
        if (id != null) {
            subject = subjectDAO.get(id);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String save()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (errors.isEmpty()) {
                if (id == null) {
                    subjectDAO.create(subject);
                } else {
                    subjectDAO.update(subject);
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Sujet enregistré.",
                        null
                ));

                return "subjects.xhtml";
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

        if (id != null) {
            return "subject_form.xhtml?id=" + id;
        } else {
            return "subject_form.xhtml";
        }
    }
}

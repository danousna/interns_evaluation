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
public class SubjectCreateBean {
    private SubjectEntity subject;
    private SubjectDAO subjectDAO;

    private List<String> errors = new ArrayList<>();

    public SubjectCreateBean() {
        this.subjectDAO = DAOFactory.getInstance().getSubjetDAO();
        this.subject = new SubjectEntity();
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

    public void create()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (errors.isEmpty()) {
                subjectDAO.create(subject);

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Sujet enregistré.",
                        null
                ));
                try {
                    context.getExternalContext().redirect("subjects.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de la création du sujet.",
                        null
                ));
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'ajout du sujet : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }
    }
}

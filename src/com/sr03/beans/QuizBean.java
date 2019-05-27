package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.QuizEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class QuizBean {
    private QuizEntity quiz;
    private QuizDAO quizDAO;

    private Long editId;

    private List<String> errors = new ArrayList<>();

    // UI
    // List of questions

    public QuizBean() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.quiz = new QuizEntity();
    }

    public void init() {
        if (editId != null) {
            quiz = quizDAO.get(editId);
        }
    }

    public QuizEntity getQuiz() {
        return quiz;
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
                    quiz.setIs_active(true);
                    quizDAO.create(quiz);
                } else {
                    quizDAO.update(quiz);
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Questionnaire enregistré.",
                        null
                ));

                return "quizzes.xhtml?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de l'enregistrement du questionnaire.",
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
            return "quiz_form?id=" + editId;
        } else {
            return "quiz_form";
        }
    }
}

package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.QuizEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class QuizCreateBean {
    private QuizEntity quiz;
    private QuizDAO quizDAO;

    private List<String> errors = new ArrayList<>();

    public QuizCreateBean() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.quiz = new QuizEntity();
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

    public void create()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (errors.isEmpty()) {
                quiz.setIs_active(true);
                quizDAO.create(quiz);

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Questionnaire enregistré.",
                        null
                ));
                try {
                    context.getExternalContext().redirect("quizzes.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de la création du questionnaire.",
                        null
                ));
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'ajout du questionnaire : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }
    }
}

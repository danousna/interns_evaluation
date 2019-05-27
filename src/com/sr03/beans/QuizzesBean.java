package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.QuizEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

@ManagedBean
@ViewScoped
public class QuizzesBean extends HttpServlet {
    private List<QuizEntity> quizzes;
    private QuizDAO quizDAO;

    public QuizzesBean() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.quizzes = quizDAO.getAll();
    }

    public List<QuizEntity> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<QuizEntity> s) { quizzes = s; }

    public String changeQuizAvailability(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        quizDAO.changeQuizAvailability(id);
        QuizEntity changedQuiz = quizDAO.get(id);
        FacesMessage message = new FacesMessage( "Questionnaire N°" + changedQuiz.getId() + " " + changedQuiz.getName() + " " + (changedQuiz.getIs_active() ? "ACTIVÉ" : "DÉSACTIVÉ"));
        context.addMessage( null, message );

//        try {
//            context.getExternalContext().redirect("quizzes.xhtml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return "success";
    }

    public String deleteQuiz(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        quizDAO.delete(id);
        FacesMessage message = new FacesMessage( "Questionnaire SUPPRIMÉ");
        context.addMessage( null, message );

        try {
            context.getExternalContext().redirect("quizzes.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
}

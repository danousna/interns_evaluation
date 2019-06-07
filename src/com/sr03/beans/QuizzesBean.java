package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.QuizEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.stream.Collectors;

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
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la modification.",
                null
        ));

        return "quizzes.xhtml";
    }

    public String delete(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        quizDAO.delete(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));

        return "quizzes.xhtml";
    }

    public List<QuizEntity> activeQuizzes() {
        return quizzes.stream()
                .filter(quiz -> quiz.getIs_active())
                .collect(Collectors.toList());
    }

    public Boolean HasCompleteQuiz(Long idUser, Long idQuiz) {
        return quizDAO.HasCompleteQuiz(idUser, idQuiz);
    }

    public Object[] GetQuizResult(Long idUser, Long idQuiz) {
        return quizDAO.GetQuizResult(idUser, idQuiz);
    }

    public Object[] GetBestInternByScore(Long idQuiz) {
        return quizDAO.GetBestInternByScore(idQuiz);
    }
}

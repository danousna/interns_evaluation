package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.dao.RecordDAO;
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
    private RecordDAO recordDAO;

    public QuizzesBean() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.recordDAO = DAOFactory.getInstance().getRecordDAO();
        this.quizzes = quizDAO.getAll();
        for(QuizEntity quiz : this.quizzes) {
            quiz.setRecords(recordDAO.getAll(quiz.getId()));
        }
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

    public List<QuizEntity> availableQuizzes() {
        Long user_id = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
        return quizzes.stream()
                .filter(QuizEntity::getIs_active)
                .filter(quiz -> !quizDAO.hasCompletedQuiz(user_id, quiz.getId()))
                .collect(Collectors.toList());
    }

    public Boolean hasCompletedQuiz(Long user_id, Long quiz_id) {
        return quizDAO.hasCompletedQuiz(user_id, quiz_id);
    }

    public Object[] quizResult(Long user_id, Long quiz_id) {
        return quizDAO.getQuizResult(user_id, quiz_id);
    }

    public Object[] bestInternByScore(Long quiz_id) {
        return quizDAO.getBestInternByScore(quiz_id);
    }
}

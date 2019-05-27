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

        return "quizzes.xhtml?faces-redirect=true";
    }

    public String delete(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        quizDAO.delete(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));

        return "quizzes.xhtml?faces-redirect=true";
    }
}

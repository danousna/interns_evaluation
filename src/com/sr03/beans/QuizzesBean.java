package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.QuizEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
}

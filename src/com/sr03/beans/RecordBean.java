package com.sr03.beans;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.dao.RecordDAO;
import com.sr03.entities.QuestionEntity;
import com.sr03.entities.QuizEntity;
import com.sr03.entities.RecordEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;

@ManagedBean
@ViewScoped
public class RecordBean extends HttpServlet {
    private RecordEntity record;
    private QuizEntity quiz;
    private HashMap<Long, Long> answers;

    private RecordDAO recordDAO;
    private QuizDAO quizDAO;

    private Long quizId;

    public RecordBean() {
        this.record = new RecordEntity();
        this.quiz = new QuizEntity();
        this.answers = new HashMap<>();

        this.recordDAO = DAOFactory.getInstance().getRecordDAO();
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
    }

    public void init() {
        if (quizId != null) {
            quiz = quizDAO.get(quizId);

            for (QuestionEntity question : quiz.getQuestions()) {
                answers.put(question.getId(), null);
            }
        }
    }

    public RecordEntity getRecord() {
        return record;
    }

    public void setRecord(RecordEntity record) {
        this.record = record;
    }

    public RecordDAO getRecordDAO() {
        return recordDAO;
    }

    public void setRecordDAO(RecordDAO recordDAO) {
        this.recordDAO = recordDAO;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public QuizDAO getQuizDAO() {
        return quizDAO;
    }

    public void setQuizDAO(QuizDAO quizDAO) {
        this.quizDAO = quizDAO;
    }

    public HashMap<Long, Long> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<Long, Long> answers) {
        this.answers = answers;
    }
}

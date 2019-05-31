package com.sr03.beans;

import com.sr03.dao.*;
import com.sr03.entities.QuestionEntity;
import com.sr03.entities.QuizEntity;
import com.sr03.entities.RecordEntity;
import com.sr03.entities.UserAnswerEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.sql.Timestamp;
import java.util.HashMap;

@ManagedBean
@ViewScoped
public class RecordBean extends HttpServlet {
    private RecordEntity record;
    private QuizEntity quiz;
    private HashMap<Long, Long> answers;

    private RecordDAO recordDAO;
    private QuizDAO quizDAO;
    private UserAnswerDAO userAnswerDAO;

    private Long quizId;

    public RecordBean() {
        this.record = new RecordEntity();
        this.quiz = new QuizEntity();
        this.answers = new HashMap<>();

        this.recordDAO = DAOFactory.getInstance().getRecordDAO();
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.userAnswerDAO = DAOFactory.getInstance().getUserAnswerDAO();
    }

    public void init() {
        if (quizId != null) {
            quiz = quizDAO.get(quizId);

            record.setQuiz_id(quiz.getId());
            record.setUser_id((Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id"));
            record.setStarted_at(new Timestamp(System.currentTimeMillis()));

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

    public void save() {
        try {
            record.setFinished_at(new Timestamp(System.currentTimeMillis()));
            recordDAO.create(record);

            for (QuestionEntity question : quiz.getQuestions()) {
                UserAnswerEntity userAnswer = new UserAnswerEntity();
                userAnswer.setUser_id(record.getUser_id());
                userAnswer.setRecord_id(record.getId());
                userAnswer.setQuestion_id(question.getId());
                userAnswer.setAnswer_id(question.getAnswer());
                userAnswerDAO.create(userAnswer);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}

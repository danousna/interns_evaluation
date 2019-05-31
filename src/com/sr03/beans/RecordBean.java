package com.sr03.beans;

import com.sr03.dao.*;
import com.sr03.entities.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.sql.Timestamp;

@ManagedBean
@ViewScoped
public class RecordBean extends HttpServlet {
    private RecordEntity record;
    private QuizEntity quiz;
    private int score;

    private RecordDAO recordDAO;
    private QuizDAO quizDAO;
    private UserAnswerDAO userAnswerDAO;

    private Long quizId;

    public RecordBean() {
        this.record = new RecordEntity();
        this.quiz = new QuizEntity();

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void save() {
        try {
            score = 0;

            record.setFinished_at(new Timestamp(System.currentTimeMillis()));
            recordDAO.create(record);

            for (QuestionEntity question : quiz.getQuestions()) {
                if (question.getAnswer() != null) {
                    UserAnswerEntity userAnswer = new UserAnswerEntity();
                    userAnswer.setUser_id(record.getUser_id());
                    userAnswer.setRecord_id(record.getId());
                    userAnswer.setQuestion_id(question.getId());
                    userAnswer.setAnswer_id(question.getAnswer());
                    userAnswerDAO.create(userAnswer);

                    for (AnswerEntity answer : question.getAnswers()) {
                        if (answer.getIs_correct() && question.getAnswer().equals(answer.getId())) {
                            score++;
                        }
                    }
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}

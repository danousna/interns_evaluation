package com.sr03.beans;

import com.sr03.dao.*;
import com.sr03.entities.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private int questionIndex;

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

            ArrayList<QuestionEntity> questions = new ArrayList<>();
            for (QuestionEntity question : quiz.getQuestions()) {
                // Only display active questions
                if (question.getIs_active()) {
                    // Only display active answers
                    question.setAnswers(question.getAnswers().stream()
                            .filter(AnswerEntity::getIs_active)
                            .collect(Collectors.toCollection(ArrayList::new))
                    );
                    questions.add(question);
                }
            }
            quiz.setQuestions(questions);

            score = 0;
            questionIndex = 0;

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

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public QuestionEntity currentQuestion() {
        return quiz.getQuestions().get(questionIndex);
    }

    public void next() {
        System.out.println(currentQuestion().getAnswer());
        for (AnswerEntity answer : currentQuestion().getAnswers()) {
            if (answer.getIs_correct() && currentQuestion().getAnswer().equals(answer.getId())) {
                score++;
            }
        }
        questionIndex++;
    }

    public void save() {
        // Call for last answer.
        next();

        try {
            record.setFinished_at(new Timestamp(System.currentTimeMillis()));
            record.setScore(score);
            recordDAO.create(record);

            for (QuestionEntity question : quiz.getQuestions()) {
                if (question.getAnswer() != null) {
                    UserAnswerEntity userAnswer = new UserAnswerEntity();
                    userAnswer.setUser_id(record.getUser_id());
                    userAnswer.setRecord_id(record.getId());
                    userAnswer.setQuestion_id(question.getId());
                    userAnswer.setAnswer_id(question.getAnswer());
                    userAnswerDAO.create(userAnswer);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}

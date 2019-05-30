package com.sr03.beans;

import com.sr03.dao.DAOException;
import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuestionDAO;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.AnswerEntity;
import com.sr03.entities.QuestionEntity;
import com.sr03.entities.QuizEntity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class QuizBean {
    private QuizEntity quiz;
    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;

    private Long id;

    private List<String> errors = new ArrayList<>();

    public QuizBean() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.questionDAO = DAOFactory.getInstance().getQuestionDAO();
        this.quiz = new QuizEntity();
    }

    public void init() {
        if (id != null) {
            quiz = quizDAO.get(id);
        }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String save()  {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (errors.isEmpty()) {
                if (id == null) {
                    quiz.setIs_active(true);
                    quizDAO.create(quiz);
                } else {
                    quizDAO.update(quiz);
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Questionnaire enregistré.",
                        null
                ));

                return "quizzes.xhtml?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Echec lors de l'enregistrement du questionnaire.",
                        null
                ));
            }
        } catch (DAOException e) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Échec de l'enregistrement : une erreur imprévue est survenue, merci de réessayer dans quelques instants.",
                    null
            ));
            e.printStackTrace();
        }

        if (id != null) {
            return "quiz_form?id=" + id;
        } else {
            return "quiz_form";
        }
    }

    public void addQuestion() {
        ArrayList<QuestionEntity> questions = quiz.getQuestions();

        if (questions == null) {
            questions = new ArrayList<>();
        }

        QuestionEntity question = new QuestionEntity();
        question.setIs_active(true);
        questions.add(question);
        quiz.setQuestions(questions);
    }

    public void addQuestionAnswer(int index) {
        ArrayList<QuestionEntity> questions = quiz.getQuestions();
        ArrayList<AnswerEntity> answers = questions.get(index).getAnswers();

        if (answers == null) {
            answers = new ArrayList<>();
        }
        AnswerEntity answer = new AnswerEntity();
        answer.setIs_active(true);
        answers.add(answer);
        questions.get(index).setAnswers(answers);

        quiz.setQuestions(questions);
    }

    public void removeQuestion(int index) {
        ArrayList<QuestionEntity> questions = quiz.getQuestions();
        questions.remove(index);
        quiz.setQuestions(questions);
    }

    public void removeQuestionAnswer(int index, int answerIndex) {
        ArrayList<QuestionEntity> questions = quiz.getQuestions();
        ArrayList<AnswerEntity> answers = questions.get(index).getAnswers();

        answers.remove(answerIndex);
        questions.get(index).setAnswers(answers);

        quiz.setQuestions(questions);
    }

    // Manage questions

    public String changeQuestionAvailability(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        questionDAO.changeQuizAvailability(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la modification.",
                null
        ));

        return "quiz.xhtml?faces-redirect=true&id=" + this.id;
    }

    public String deleteQuestion(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        questionDAO.delete(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));

        return "quizzes.xhtml?faces-redirect=true&id=" + this.id;
    }
}

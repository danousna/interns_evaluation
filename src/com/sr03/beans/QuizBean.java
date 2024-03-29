package com.sr03.beans;

import com.sr03.dao.*;
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
    private AnswerDAO answerDAO;
    private RecordDAO recordDAO;

    private Long id;

    private List<String> errors = new ArrayList<>();

    public QuizBean() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
        this.questionDAO = DAOFactory.getInstance().getQuestionDAO();
        this.answerDAO = DAOFactory.getInstance().getAnswerDAO();
        this.recordDAO = DAOFactory.getInstance().getRecordDAO();
        this.quiz = new QuizEntity();
    }

    public void init() {
        if (id != null) {
            quiz = quizDAO.get(id);
            quiz.setRecords(recordDAO.getAll(quiz.getId()));

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

        validateQuestions();

        try {
            if (errors.isEmpty()) {
                if (id == null) {
                    quiz.setIs_active(true);
                    quizDAO.create(quiz);
                } else {
                    if (quiz.getRecords().size() == 0) {
                        quizDAO.update(quiz);
                    }
                }

                context.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Questionnaire enregistré.",
                        null
                ));

                return "quizzes.xhtml";
            } else {
                for (String error : errors) {
                    context.addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            error,
                            null
                    ));
                }
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
            return "quiz_form.xhtml?id=" + id;
        } else {
            return "quiz_form.xhtml";
        }
    }

    private void validateQuestions() {
        for (QuestionEntity question : quiz.getQuestions()) {
            int correctCount = 0;

            for (AnswerEntity answer : question.getAnswers()) {
                if (answer.getIs_correct()) {
                    correctCount++;
                }
            }

            if (correctCount > 1) {
                errors.add("Une question ne peut avoir qu'une seule bonne réponse.");
            }
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
        QuestionEntity question = questions.get(index);

        if (question.getId() != null) {
            questionDAO.delete(question.getId());
        }

        questions.remove(index);
        quiz.setQuestions(questions);
    }

    public void removeQuestionAnswer(int index, int answerIndex) {
        ArrayList<QuestionEntity> questions = quiz.getQuestions();
        ArrayList<AnswerEntity> answers = questions.get(index).getAnswers();
        AnswerEntity answer = answers.get(answerIndex);

        if (answer.getId() != null) {
            answerDAO.delete(answer.getId());
        }

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

        return "quiz.xhtml?faces-redirect=true&id=  " + this.id;
    }

    public String deleteQuestion(Long id) {
        FacesContext context = FacesContext.getCurrentInstance();

        questionDAO.delete(id);
        context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès de la suppression.",
                null
        ));

        return "quiz.xhtml?id=" + this.id;
    }
}

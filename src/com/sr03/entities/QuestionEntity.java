package com.sr03.entities;

import java.util.ArrayList;

public class QuestionEntity {
    private Long id;
    private String body;
    private Boolean is_active;
    private Long order;
    private Long quiz_id;

    private QuizEntity quiz;
    private ArrayList<AnswerEntity> answers;

    public QuestionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public ArrayList<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerEntity> answers) {
        this.answers = answers;
    }
}

package com.sr03.entities;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RecordEntity {
    private Long id;
    private Long quiz_id;
    private Long user_id;
    private Timestamp started_at;
    private Timestamp finished_at;

    private ArrayList<UserAnswerEntity> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Timestamp getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Timestamp started_at) {
        this.started_at = started_at;
    }

    public Timestamp getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Timestamp finished_at) {
        this.finished_at = finished_at;
    }

    public ArrayList<UserAnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<UserAnswerEntity> answers) {
        this.answers = answers;
    }
}

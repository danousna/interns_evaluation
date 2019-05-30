package com.sr03.entities;

public class AnswerEntity implements Comparable<AnswerEntity> {
    private Long id;
    private String body;
    private Boolean is_active;
    private Boolean is_correct;
    private Long order;
    private Long question_id;

    public AnswerEntity() {

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

    public Boolean getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(Boolean is_correct) {
        this.is_correct = is_correct;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    @Override
    public int compareTo(AnswerEntity a) {
        return (int) (order - a.order);
    }
}

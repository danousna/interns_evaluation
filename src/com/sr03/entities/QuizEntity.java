package com.sr03.entities;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class QuizEntity implements Serializable {
    private Long id;
    private String name;
    private Boolean is_active;
    private Long subject_id;

    public QuizEntity() {
    }

    public QuizEntity(Long id, String name, Boolean is_active, Long subject_id) {
        this.id = id;
        this.name = name;
        this.is_active = is_active;
        this.subject_id = subject_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }
}

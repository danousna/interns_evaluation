package com.sr03.entities;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class SubjectEntity implements Serializable {
    private Long id;
    private String name;

    public SubjectEntity() {
    }

    public SubjectEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }
}

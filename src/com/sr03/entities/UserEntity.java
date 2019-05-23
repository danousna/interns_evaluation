package com.sr03.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UserEntity implements Serializable {
    private Long id;
    private Boolean is_active;
    private Boolean is_admin;
    private String name;
    private String email;
    private String password;
    private String company;
    private String phone;
    private Timestamp created_at;

    public UserEntity() {

    }

    public UserEntity(Long id, Boolean is_active, Boolean is_admin, String name, String email, String password, String company, String phone, Timestamp created_at) {
        this.id = id;
        this.is_active = is_active;
        this.is_admin = is_admin;
        this.name = name;
        this.email = email;
        this.password = password;
        this.company = company;
        this.phone = phone;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}


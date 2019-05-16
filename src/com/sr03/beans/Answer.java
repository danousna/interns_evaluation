package com.sr03.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Answer implements Serializable{
    private int id;
    private String valeur;
    private boolean actif;
    private boolean correct;
    private boolean ordre;
    private int id_question;

    public Answer() {

    }

    public Answer(int id, String valeur, boolean actif, boolean correct, boolean ordre, int id_question) {
        this.id = id;
        this.valeur = valeur;
        this.actif = actif;
        this.correct = correct;
        this.ordre = ordre;
        this.id_question = id_question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isOrdre() {
        return ordre;
    }

    public void setOrdre(boolean ordre) {
        this.ordre = ordre;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", valeur='" + valeur + '\'' +
                ", actif=" + actif +
                ", correct=" + correct +
                ", ordre=" + ordre +
                ", id_question=" + id_question +
                '}';
    }
}

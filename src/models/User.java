package models;

import java.util.ArrayList;

public class User {
    private int id;
    private String email;
    private String mot_de_passe;
    private String nom;
    private String societe;
    private String telephone;
    private String date_creation;
    private boolean actif;
    private boolean est_admin;

    public User(int id, String email, String mot_de_passe, String nom, String societe, String telephone, String date_creation, boolean actif, boolean est_admin) {
        this.id = id;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.nom = nom;
        this.societe = societe;
        this.telephone = telephone;
        this.date_creation = date_creation;
        this.actif = actif;
        this.est_admin = est_admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isEst_admin() {
        return est_admin;
    }

    public void setEst_admin(boolean est_admin) {
        this.est_admin = est_admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", nom='" + nom + '\'' +
                ", societe='" + societe + '\'' +
                ", telephone='" + telephone + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", actif=" + actif +
                ", est_admin=" + est_admin +
                '}';
    }
}

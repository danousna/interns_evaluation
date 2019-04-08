package models;

public class Parcours {
    private int id;
    private int score;
    private String date_debut;
    private String date_fin;
    private int id_questionnaire;

    public Parcours(int id, int score, String date_debut, String date_fin, int id_questionnaire) {
        this.id = id;
        this.score = score;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_questionnaire = id_questionnaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public int getId_questionnaire() {
        return id_questionnaire;
    }

    public void setId_questionnaire(int id_questionnaire) {
        this.id_questionnaire = id_questionnaire;
    }

    @Override
    public String toString() {
        return "Parcours{" +
                "id=" + id +
                ", score=" + score +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                ", id_questionnaire=" + id_questionnaire +
                '}';
    }
}

package Model;

public class Questionnaire {
    private int id;
    private String nom;
    private boolean actif;
    private int id_sujet;

    public Questionnaire(int id, String nom, boolean actif, int id_sujet) {
        this.id = id;
        this.nom = nom;
        this.actif = actif;
        this.id_sujet = id_sujet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public int getId_sujet() {
        return id_sujet;
    }

    public void setId_sujet(int id_sujet) {
        this.id_sujet = id_sujet;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", actif=" + actif +
                ", id_sujet=" + id_sujet +
                '}';
    }
}

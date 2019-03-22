package Model;

public class Reponse_utilisateur {
    private int id_utilisateur;
    private int id_reponse;

    public Reponse_utilisateur(int id_utilisateur, int id_reponse) {
        this.id_utilisateur = id_utilisateur;
        this.id_reponse = id_reponse;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    @Override
    public String toString() {
        return "Reponse_utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", id_reponse=" + id_reponse +
                '}';
    }
}

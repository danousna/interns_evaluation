package Model;

public class Question {
    private int id;
    private String enonce;
    private boolean actif;
    private  boolean ordre;
    private int id_questionnaire;

    public Question(int id, String enonce, boolean actif, boolean ordre, int id_questionnaire) {
        this.id = id;
        this.enonce = enonce;
        this.actif = actif;
        this.ordre = ordre;
        this.id_questionnaire = id_questionnaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isOrdre() {
        return ordre;
    }

    public void setOrdre(boolean ordre) {
        this.ordre = ordre;
    }

    public int getId_questionnaire() {
        return id_questionnaire;
    }

    public void setId_questionnaire(int id_questionnaire) {
        this.id_questionnaire = id_questionnaire;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", enonce='" + enonce + '\'' +
                ", actif=" + actif +
                ", ordre=" + ordre +
                ", id_questionnaire=" + id_questionnaire +
                '}';
    }
}

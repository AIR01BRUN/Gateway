package medilabo.model;

public class History {

    private String id;

    private int idPatient;

    private String namePatient;

    private String note;

    public History() {
    }

    public History(String id, int idPatient, String namePatient, String note) {
        this.id = id;
        this.idPatient = idPatient;
        this.namePatient = namePatient;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
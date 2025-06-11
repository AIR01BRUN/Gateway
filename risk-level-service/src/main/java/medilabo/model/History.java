package medilabo.model;

public class History {

    private String id;

    private String idPatient;

    private String namePatient;

    private String note;

    private String riskLevel;

    public History() {
    }

    public History(String id, String idPatient, String namePatient, String note) {
        this.id = id;
        this.idPatient = idPatient;
        this.namePatient = namePatient;
        this.note = note;
        this.riskLevel = "unknown"; // Default risk level
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

}
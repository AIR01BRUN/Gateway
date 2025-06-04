package medilabo.model;

public class DiabetesReport {

    private String id;

    private String risk;

    public DiabetesReport() {
    }

    public DiabetesReport(String id, String risk) {
        this.id = id;
        this.risk = risk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }
}
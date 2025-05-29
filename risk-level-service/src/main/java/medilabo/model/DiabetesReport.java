package medilabo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "diabetes_reports")
public class DiabetesReport {

    @Id
    private String id;

    @Field("risk")
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
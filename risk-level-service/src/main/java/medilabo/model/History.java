package medilabo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "history")
public class History {

    @Id
    private String id;

    @Field("id_patient")
    private int idPatient;

    @Field("name_patient")
    private String namePatient;

    @Field("note")
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
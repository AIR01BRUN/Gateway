package medilabo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import medilabo.model.DiabetesReport;
import medilabo.model.History;
import medilabo.model.Patient;

@Service
public class RiskLevelService {

    private final RestTemplate restTemplate;

    @Value("${patient.service.url}")
    private String patientServiceUrl;

    @Value("${history.service.url}")
    private String historyServiceUrl;

    @Value("${diabetes-report.service.url}")
    private String diabetesReportServiceUrl;

    private final String[] diabetesReportFields = {
            "Hémoglobine A1C",
            "Microalbumine", "Taille",
            "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol",
            "Vertiges", "Rechute", "Réaction",
            "Anticorps"
    };

    public RiskLevelService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int countTrigger(String note) {
        if (note == null || note.trim().isEmpty()) {
            return 0;
        }

        String normalizedNote = note.toLowerCase();
        int count = 0;

        for (String field : diabetesReportFields) {
            String normalizedField = field.toLowerCase();
            String wordPattern = "\\b" + normalizedField + "\\b";
            if (normalizedNote.matches(".*" + wordPattern + ".*")) {
                count++;

            }
        }

        return count;
    }

    public String decideRiskLevel(String idPatient) {
        Patient patient = restTemplate.getForObject(patientServiceUrl + "/patient/" + idPatient,
                Patient.class);
        History history = restTemplate.getForObject(historyServiceUrl + "/history/" + idPatient,
                History.class);
        if (patient == null || history == null) {
            return "Patient or history not found";
        }

        int age = calculateAge(patient.getBirthDate());

        int countTrigger = countTrigger(history.getNote());

        String riskLevel = "None";
        if (isBorderline(countTrigger, age)) {
            riskLevel = "Borderline";
        }
        if (isInDanger(patient.getGender(), age, countTrigger)) {
            riskLevel = "In Danger";
        }
        if (isEarlyOnset(patient.getGender(), age, countTrigger)) {
            riskLevel = "Early Onset";
        }

        return riskLevel;
    }

    public void assignRiskLevel(DiabetesReport diabetesReport) {
        String riskLevel = decideRiskLevel(diabetesReport.getId());
        diabetesReport.setRisk(riskLevel);

        restTemplate.put(diabetesReportServiceUrl + "/reports/update",
                diabetesReport, DiabetesReport.class);
    }

    public int calculateAge(String dateOfBirth) {
        String[] parts = dateOfBirth.split("/");
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[0]);

        int age = 2023 - year;
        if (month > 10 || (month == 10 && day > 10)) {
            age--;
        }
        return age;
    }

    public boolean isBorderline(int countTrigger, int age) {
        return countTrigger > 2 && countTrigger <= 5 && age > 30;
    }

    public boolean isInDanger(String sexe, int age, int countTrigger) {
        if (sexe.equals("H") && countTrigger >= 3 && age <= 30) {
            return true;
        } else if (sexe.equals("F") && countTrigger >= 4 && age <= 30) {
            return true;
        } else if (countTrigger >= 6 && age > 30) {
            return true;
        }
        return false;
    }

    public boolean isEarlyOnset(String sexe, int age, int countTrigger) {
        if (sexe.equals("H") && countTrigger >= 5 && age <= 30) {
            return true;
        } else if (sexe.equals("F") && countTrigger >= 7 && age <= 30) {
            return true;
        } else if (countTrigger >= 8 && age > 30) {
            return true;
        }
        return false;
    }

}
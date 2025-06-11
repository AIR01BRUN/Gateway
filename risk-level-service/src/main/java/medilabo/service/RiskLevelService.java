package medilabo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import medilabo.model.History;
import medilabo.model.Patient;

@Service
public class RiskLevelService {

    private final RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;

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

    public String decideRiskLevel(History history) {
        Patient patient = restTemplate.getForObject(gatewayUrl + "/patient/" + history.getIdPatient(),
                Patient.class);

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
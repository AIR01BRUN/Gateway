package medilabo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import medilabo.model.Patient;
import medilabo.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient getPatientByIdt(String id) {
        return patientRepository.findById(id)
                .orElse(null);
    }

    public boolean updatePatient(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            patientRepository.save(patient);
            return true;
        }
        return false;
    }

    public boolean deletePatient(String id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;

    }

}

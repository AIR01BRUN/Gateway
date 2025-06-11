package medilabo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import medilabo.model.Patient;
import medilabo.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

    public final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public List<Patient> getAllPatients() {

        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable String id) {
        return patientService.getPatientByIdt(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {

        patientService.addPatient(patient);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/update")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient) {
        if (patientService.updatePatient(patient)) {
            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable String id) {
        if (patientService.deletePatient(id)) {
            return ResponseEntity.ok(id + " deleted successfully.");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "patients_service OK";
    }

}

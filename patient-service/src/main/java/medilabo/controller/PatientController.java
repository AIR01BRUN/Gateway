package medilabo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
    public String addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        return "added";
    }

    @PutMapping("/update")
    public String updatePatient(@RequestBody Patient patient) {
        if (patientService.updatePatient(patient)) {
            return "updated";
        }
        return "NOT updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        if (patientService.deletePatient(id)) {
            return "deleted";
        }

        return "Not deleted";
    }

    @GetMapping("/ping")
    public String ping() {
        return "patients_service OK";
    }

}

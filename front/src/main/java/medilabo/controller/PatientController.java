package medilabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import medilabo.model.Patient;

@Controller
@RequestMapping("/p")
public class PatientController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    public PatientController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("Fetching patient list from: " + gatewayUrl + "/patient/all");
        List<Patient> listPatients = restTemplate.getForObject(gatewayUrl + "/patient/all", List.class);
        model.addAttribute("patients", listPatients);
        return "patient/list"; // Returns the name of the view to be rendered
    }

    @GetMapping("/add")
    public String addPatient() {
        return "patient/add"; // Returns the name of the view to be rendered for adding a patient
    }

    @GetMapping("/edit")
    public String editPatient(@PathVariable("id") Integer id, Model model) {

        return "patient/edit"; // Returns the name of the view to be rendered for editing a patient
    }
}

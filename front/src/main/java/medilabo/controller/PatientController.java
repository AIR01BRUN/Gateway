package medilabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import medilabo.model.History;
import medilabo.model.Patient;

@Controller
@RequestMapping("/p")
public class PatientController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.url-local}")
    private String gatewayUrlLocal;

    @Value("${patient.url}")
    private String patientUrl;

    public PatientController() {

    }

    @GetMapping("/list")
    public String list(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        List<Patient> listPatients = restTemplate.getForObject(patientUrl + "/patient/all", List.class);
        model.addAttribute("patients", listPatients);
        return "patient/list"; // Returns the name of the view to be rendered
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("gatewayUrl", patientUrl);
        return "patient/add";
    }

    @PostMapping("/add")
    public String addSubmit(Patient patient) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(patientUrl + "/patient/add", patient, Patient.class);
        return "redirect:" + gatewayUrlLocal + "/p/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        // Fetch patient data from the API
        RestTemplate restTemplate = new RestTemplate();
        Patient patient = restTemplate.getForObject(patientUrl + "/patient/" + id, Patient.class);

        model.addAttribute("patient", patient);
        model.addAttribute("gatewayUrl", gatewayUrl);
        return "patient/edit"; // Returns the edit view
    }

    @PostMapping("/edit")
    public String editSubmit(Patient patient) {
        // Use HttpEntity to send the patient object in a PUT request
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Patient> request = new HttpEntity<>(patient);
        restTemplate.exchange(patientUrl + "/patient/update", HttpMethod.PUT, request, Patient.class);
        return "redirect:" + gatewayUrlLocal + "/p/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubmit(@PathVariable("id") String id) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(patientUrl + "/patient/delete/" + id);
        return "redirect:" + gatewayUrlLocal + "/p/list"; // Redirect to the patient list
                                                          // after submission
    }

}

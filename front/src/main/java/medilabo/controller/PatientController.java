package medilabo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import medilabo.model.Patient;

@Controller
@RequestMapping("/p")
public class PatientController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.url-local}")
    private String gatewayUrlLocal;

    private RestTemplate restTemplate;

    public PatientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Centralise la récupération du token dans les headers
    private HttpHeaders HeaderEntity(HttpServletRequest request) {
        String token = request.getHeader("X-Gateway-Token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Gateway-Token", token);
        return headers;
    }

    // LIST
    @GetMapping("/list")
    public String list(HttpServletRequest request, Model model) {
        ResponseEntity<List<Patient>> response = restTemplate.exchange(
                gatewayUrl + "/patient/all",
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                new ParameterizedTypeReference<List<Patient>>() {
                });
        model.addAttribute("patients", response.getBody());
        return "patient/list";
    }

    // ADD - formulaire
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model) {
        model.addAttribute("gatewayUrl", gatewayUrl);
        return "patient/add";
    }

    // ADD - soumission
    @PostMapping("/add")
    public String addSubmit(HttpServletRequest request, Patient patient) {
        HttpEntity<Patient> entity = new HttpEntity<>(patient, HeaderEntity(request));
        restTemplate.exchange(
                gatewayUrl + "/patient/add",
                HttpMethod.POST,
                entity,
                Patient.class);
        return "redirect:" + gatewayUrlLocal + "/p/list";
    }

    // EDIT - formulaire
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        ResponseEntity<Patient> response = restTemplate.exchange(
                gatewayUrl + "/patient/" + id,
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                Patient.class);
        model.addAttribute("patient", response.getBody());
        model.addAttribute("gatewayUrl", gatewayUrl);
        return "patient/edit";
    }

    // EDIT - soumission
    @PostMapping("/edit")
    public String editSubmit(HttpServletRequest request, Patient patient) {
        HttpEntity<Patient> entity = new HttpEntity<>(patient, HeaderEntity(request));
        restTemplate.exchange(
                gatewayUrl + "/patient/update",
                HttpMethod.PUT,
                entity,
                Patient.class);
        return "redirect:" + gatewayUrlLocal + "/p/list";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteSubmit(@PathVariable("id") String id, HttpServletRequest request) {
        restTemplate.exchange(
                gatewayUrl + "/patient/delete/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(HeaderEntity(request)),
                Void.class);
        return "redirect:" + gatewayUrlLocal + "/p/list";
    }
}

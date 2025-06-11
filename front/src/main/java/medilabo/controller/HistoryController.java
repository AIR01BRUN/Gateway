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

import medilabo.model.History;
import medilabo.model.Patient;

@Controller
@RequestMapping("/h")
public class HistoryController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.url-local}")
    private String gatewayUrlLocal;

    private RestTemplate restTemplate;

    public HistoryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders HeaderEntity(HttpServletRequest request) {
        String token = request.getHeader("X-Gateway-Token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Gateway-Token", token);
        return headers;
    }

    @GetMapping("/list/{idPatient}")
    public String list(@PathVariable("idPatient") String idPatient, HttpServletRequest request, Model model) {
        ResponseEntity<List<History>> response = restTemplate.exchange(
                gatewayUrl + "/history/byIdPatient/" + idPatient,
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                new ParameterizedTypeReference<List<History>>() {
                });
        model.addAttribute("histories", response.getBody());
        model.addAttribute("idPatient", idPatient);
        return "history/list";
    }

    @GetMapping("/add/{idPatient}")
    public String add(@PathVariable("idPatient") String idPatient, HttpServletRequest request, Model model) {
        model.addAttribute("gatewayUrl", gatewayUrl);

        ResponseEntity<Patient> patientResponse = restTemplate.exchange(
                gatewayUrl + "/patient/" + idPatient,
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                Patient.class);

        Patient patient = patientResponse.getBody();
        History history = new History();
        history.setIdPatient(idPatient);
        history.setNamePatient(patient.getFirstName() + " " + patient.getLastName());

        model.addAttribute("history", history);
        return "history/add";
    }

    @PostMapping("/add")
    public String addSubmit(History history, HttpServletRequest request) {
        ResponseEntity<List<History>> response = restTemplate.exchange(
                gatewayUrl + "/history/all",
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                new ParameterizedTypeReference<List<History>>() {
                });
        int size = response.getBody().size() + 1;
        history.setId("" + size);

        HttpEntity<History> entity = new HttpEntity<>(history, HeaderEntity(request));

        restTemplate.exchange(
                gatewayUrl + "/history/add",
                HttpMethod.POST,
                entity,
                History.class);

        return "redirect:" + gatewayUrlLocal + "/h/list/" + history.getIdPatient();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        ResponseEntity<History> response = restTemplate.exchange(
                gatewayUrl + "/history/" + id,
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                History.class);

        model.addAttribute("history", response.getBody());
        model.addAttribute("gatewayUrl", gatewayUrl);
        return "history/edit";
    }

    @PostMapping("/edit")
    public String editSubmit(History history, HttpServletRequest request) {
        HttpEntity<History> entity = new HttpEntity<>(history, HeaderEntity(request));

        restTemplate.exchange(
                gatewayUrl + "/history/update",
                HttpMethod.PUT,
                entity,
                History.class);

        return "redirect:" + gatewayUrlLocal + "/h/list/" + history.getIdPatient();
    }

    @GetMapping("/delete/{id}")
    public String deleteSubmit(@PathVariable("id") String id, HttpServletRequest request) {
        restTemplate.exchange(
                gatewayUrl + "/history/delete/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(HeaderEntity(request)),
                Void.class);
        return "redirect:" + gatewayUrlLocal + "/p/list/";
    }

    @GetMapping("/risk-level/{idNote}")
    public String riskLevel(@PathVariable("idNote") String idNote, HttpServletRequest request) {
        ResponseEntity<History> historyResponse = restTemplate.exchange(
                gatewayUrl + "/history/" + idNote,
                HttpMethod.GET,
                new HttpEntity<>(HeaderEntity(request)),
                History.class);

        History history = historyResponse.getBody();

        HttpEntity<History> entity = new HttpEntity<>(history, HeaderEntity(request));

        ResponseEntity<String> riskResponse = restTemplate.exchange(
                gatewayUrl + "/risk-level/decide",
                HttpMethod.POST,
                entity,
                String.class);

        history.setRiskLevel(riskResponse.getBody());

        HttpEntity<History> updateEntity = new HttpEntity<>(history, HeaderEntity(request));
        restTemplate.exchange(
                gatewayUrl + "/history/update",
                HttpMethod.PUT,
                updateEntity,
                History.class);

        return "redirect:" + gatewayUrlLocal + "/h/list/" + history.getIdPatient();
    }
}

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
@RequestMapping("/h")
public class HistoryController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.url-local}")
    private String gatewayUrlLocal;

    @Value("${history.url}")
    private String historyUrl;

    @Value("${risk.url}")
    private String riskUrl;

    public HistoryController() {

    }

    @GetMapping("/list/{idPatient}")
    public String list(@PathVariable("idPatient") String idPatient, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        List<History> listHistories = restTemplate.getForObject(historyUrl + "/history/byIdPatient/" + idPatient,
                List.class);
        model.addAttribute("histories", listHistories);
        model.addAttribute("idPatient", idPatient);
        return "history/list"; // Returns the name of the view to be rendered
    }

    @GetMapping("/add/{idPatient}")
    public String add(@PathVariable("idPatient") String idPatient, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        model.addAttribute("gatewayUrl", gatewayUrl);
        Patient patient = restTemplate.getForObject(historyUrl + "/patient/" + idPatient, Patient.class);
        History history = new History();
        history.setIdPatient(idPatient);
        history.setNamePatient(patient.getFirstName() + " " + patient.getLastName());

        model.addAttribute("history", history);

        // Initialize a new History object
        return "history/add";
    }

    @PostMapping("/add")
    public String addSubmit(History history) {
        RestTemplate restTemplate = new RestTemplate();
        List<History> listHistories = restTemplate.getForObject(historyUrl + "/history/all", List.class);
        int size = listHistories.size() + 1;
        history.setId("" + size);

        restTemplate.postForObject(gatewayUrl + "/history/add", history, History.class);

        String redirection = "redirect:" + gatewayUrlLocal + "/h/list/" + history.getIdPatient();
        return redirection; // Redirect to the patient list after submission
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        // Fetch patient data from the API
        RestTemplate restTemplate = new RestTemplate();
        History history = restTemplate.getForObject(historyUrl + "/history/" + id, History.class);

        model.addAttribute("history", history);
        model.addAttribute("gatewayUrl", gatewayUrl);
        return "history/edit"; // Returns the edit view
    }

    @PostMapping("/edit")
    public String editSubmit(History history) {
        // Use HttpEntity to send the patient object in a PUT request
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<History> request = new HttpEntity<>(history);
        restTemplate.exchange(historyUrl + "/history/update", HttpMethod.PUT, request, History.class);
        String redirection = "redirect:" + gatewayUrlLocal + "/h/list/" + history.getIdPatient();
        return redirection;
    }

    @GetMapping("/delete/{id}")
    public String deleteSubmit(@PathVariable("id") String id) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(historyUrl + "/history/delete/" + id);
        String redirection = "redirect:" + gatewayUrlLocal + "/p/list/";
        return redirection;
    }

    @GetMapping("/risk-level/{idNote}")
    public String riskLevel(@PathVariable("idNote") String idNote, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        History history = restTemplate.getForObject(historyUrl + "/history/" + idNote, History.class);
        restTemplate.postForObject(riskUrl + "/risk-level/decide", history, History.class);
        String redirection = "redirect:" + gatewayUrlLocal + "/h/list/" + history.getIdPatient();
        return redirection;
    }

}
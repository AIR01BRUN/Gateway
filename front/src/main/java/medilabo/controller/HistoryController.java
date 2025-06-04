package medilabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import medilabo.model.History;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    public HistoryController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<History> listHistories = restTemplate.getForObject(gatewayUrl + "/history/list", List.class);
        model.addAttribute("histories", listHistories);
        return "history/list"; // Returns the name of the view to be rendered
    }

    @GetMapping("/add")
    public String addHistory() {
        return "history/add"; // Returns the name of the view to be rendered for adding a history
    }

    @GetMapping("/edit/{id}")
    public String editHistory(@PathVariable("id") String id, Model model) {
        // Note: Assuming id is a String as per the History entity
        return "history/edit"; // Returns the name of the view to be rendered for editing a history
    }
}
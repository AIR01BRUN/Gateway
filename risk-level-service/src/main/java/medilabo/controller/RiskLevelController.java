package medilabo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import medilabo.model.History;
import medilabo.service.RiskLevelService;

@RestController
@RequestMapping("/risk-level")
// @CrossOrigin(origins = "*")
public class RiskLevelController {

    private final RiskLevelService riskLevelService;

    @Value("${history.url}")
    private String historyUrl;

    public RiskLevelController(RiskLevelService riskLevelService) {
        this.riskLevelService = riskLevelService;
    }

    @PostMapping("/decide")
    public ResponseEntity<History> decideRiskLevel(@RequestBody History history) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<History> request = new HttpEntity<>(riskLevelService.decideRiskLevel(history));
        restTemplate.exchange(historyUrl + "/history/update", HttpMethod.PUT, request, History.class);
        return ResponseEntity.ok(history);
    }
}

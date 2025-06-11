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

    @Value("${gateway.url}")
    private String gatewayUrl;

    public RiskLevelController(RiskLevelService riskLevelService) {
        this.riskLevelService = riskLevelService;
    }

    @PostMapping("/decide")
    public ResponseEntity<String> decideRiskLevel(@RequestBody History history) {

        String risk = riskLevelService.decideRiskLevel(history);
        return ResponseEntity.ok(risk);
    }
}

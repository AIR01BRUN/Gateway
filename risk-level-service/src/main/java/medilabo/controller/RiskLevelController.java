package medilabo.controller;

import org.springframework.web.bind.annotation.*;

import medilabo.service.RiskLevelService;

@RestController
@RequestMapping("/risk-level")
// @CrossOrigin(origins = "*")
public class RiskLevelController {

    private final RiskLevelService riskLevelService;

    public RiskLevelController(RiskLevelService riskLevelService) {
        this.riskLevelService = riskLevelService;
    }

    @GetMapping("/{idPatient}")
    public String getRiskLevel(@PathVariable String idPatient) {
        return riskLevelService.decideRiskLevel(idPatient);
    }
}

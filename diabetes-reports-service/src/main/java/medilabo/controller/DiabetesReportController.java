package medilabo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import medilabo.model.DiabetesReport;
import medilabo.service.DiabetesReportService;

@RestController
@RequestMapping("/diabetes-report")
public class DiabetesReportController {

    private final DiabetesReportService diabetesReportService;

    public DiabetesReportController(DiabetesReportService diabetesReportService) {
        this.diabetesReportService = diabetesReportService;
    }

    @GetMapping("/all")
    public List<DiabetesReport> getAllReports() {
        return diabetesReportService.getAllReports();
    }

    @GetMapping("/{id}")
    public DiabetesReport getReportById(@PathVariable String id) {
        return diabetesReportService.getReportById(id);
    }

    @PostMapping("/add")
    public String addReport(@RequestBody DiabetesReport diabetesReport) {
        diabetesReportService.addReport(diabetesReport);
        return "Report added";
    }

    @PutMapping("/update")
    public String updateReport(@RequestBody DiabetesReport diabetesReport) {
        if (diabetesReportService.updateReport(diabetesReport)) {
            return "Report updated";
        }
        return "Report not updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReport(@PathVariable String id) {
        if (diabetesReportService.deleteReport(id)) {
            return "Report deleted";
        }
        return "Report not deleted";
    }

    @GetMapping("/ping")
    public String ping() {
        return "diabetes_report_service OK";
    }
}

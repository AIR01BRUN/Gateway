package medilabo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import medilabo.model.DiabetesReport;
import medilabo.repository.DiabetesReportRepository;

@Service
public class DiabetesReportService {

    private final DiabetesReportRepository diabetesReportRepository;

    public DiabetesReportService(DiabetesReportRepository diabetesReportRepository) {
        this.diabetesReportRepository = diabetesReportRepository;
    }

    public List<DiabetesReport> getAllReports() {
        return diabetesReportRepository.findAll();
    }

    public DiabetesReport getReportById(String id) {
        return diabetesReportRepository.findById(id).orElse(null);
    }

    public void addReport(DiabetesReport diabetesReport) {
        diabetesReportRepository.save(diabetesReport);
    }

    public boolean updateReport(DiabetesReport diabetesReport) {
        if (diabetesReportRepository.existsById(diabetesReport.getId())) {
            diabetesReportRepository.save(diabetesReport);
            return true;
        }
        return false;
    }

    public boolean deleteReport(String id) {
        if (diabetesReportRepository.existsById(id)) {
            diabetesReportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
package medilabo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import medilabo.model.DiabetesReport;

@Repository
public interface DiabetesReportRepository extends MongoRepository<DiabetesReport, String> {

}
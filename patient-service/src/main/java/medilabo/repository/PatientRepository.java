package medilabo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import medilabo.model.Patient;
import java.util.List;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    Optional<Patient> findById(String id);
}
package medilabo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import medilabo.model.History;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

    Optional<List<History>> findByNamePatient(String namePatient);

    Optional<History> findByIdPatient(String idPatient);
}
package medilabo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import medilabo.model.History;
import medilabo.repository.HistoryRepository;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<History> getsByName(String namePatient) {
        return historyRepository.findByNamePatient(namePatient).orElse(null);

    }

    public History getByIdPatient(String id) {
        return historyRepository.findByIdPatient(id).orElse(null);
    }

    public List<History> getAll() {
        return historyRepository.findAll();
    }

    public boolean addHistory(History history) {
        if (historyRepository.existsById(history.getId())) {
            historyRepository.save(history);
            return true;
        }
        return false;
    }

    public boolean updateHistory(History history) {
        if (historyRepository.existsById(history.getId())) {
            historyRepository.save(history);
            return true;
        }
        return false;
    }

    public boolean deleteHistory(String id) {
        if (historyRepository.existsById(id)) {
            historyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean addNote(History history, String note) {
        if (history != null) {
            history.setNote(note);
            historyRepository.save(history);
            return true;
        }
        return false;
    }

}
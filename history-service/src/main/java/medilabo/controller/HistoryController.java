package medilabo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import medilabo.model.History;
import medilabo.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    // ALL
    @GetMapping("/all")
    public List<History> getAllHistory() {
        return historyService.getAll();
    }

    // Get par le l'id du patient
    @GetMapping("/byIdPatient/{idPatient}")
    public List<History> getHistoryByIdPatient(@PathVariable String idPatient) {
        return historyService.getByIdPatient(idPatient);
    }

    // GET
    @GetMapping("/{id}")
    public History getHistoryById(@PathVariable String id) {
        return historyService.getById(id);
    }

    // ADD
    @PostMapping("/add")
    public ResponseEntity<History> add(@Valid @RequestBody History history) {

        historyService.addHistory(history);
        return ResponseEntity.ok(history);
    }

    // UPDATE
    @PutMapping("/update")
    public ResponseEntity<History> update(@Valid @RequestBody History history) {
        if (historyService.updateHistory(history)) {
            return ResponseEntity.ok(history);
        }
        return ResponseEntity.notFound().build();
    }

    // DEL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (historyService.deleteHistory(id)) {
            return ResponseEntity.ok(id + " deleted successfully.");
        }

        return ResponseEntity.notFound().build();
    }
}

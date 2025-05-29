package medilabo.controller;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/all")
    public List<History> getAllHistory() {
        return historyService.getAll();
    }

    @GetMapping("/byName/{namePatient}")
    public List<History> getHistoryByName(@PathVariable String namePatient) {
        return historyService.getsByName(namePatient);
    }

    @GetMapping("/{idPatient}")
    public History getHistoryByIdPatient(@PathVariable String idPatient) {
        return historyService.getByIdPatient(idPatient);
    }

    @PostMapping("/add")
    public String addHistory(@RequestBody History history) {
        if (historyService.addHistory(history)) {
            return "History added";
        } else {
            return "History not added";
        }
    }

    @PutMapping("/update")
    public String updateHistory(@RequestBody History history) {
        if (historyService.updateHistory(history))
            return "History updated";
        else
            return "History not updated";
    }

    @PatchMapping("/note/{id}")
    public String addNote(@PathVariable String id, @RequestBody String note) {
        if (historyService.addNote(historyService.getByIdPatient(id), note))
            return "Note added";
        else
            return "Note not added";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHistory(@PathVariable String id) {
        if (historyService.deleteHistory(id))
            return "History deleted";
        else
            return "History not deleted";
    }
}

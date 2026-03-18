package Group6project.MediTrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Group6project.MediTrack.model.DoseInstance;
import Group6project.MediTrack.model.DoseStatus;
import Group6project.MediTrack.repo.DoseInstanceRepository;

@Controller
@RequestMapping("/doses")
public class DoseLogController {

    private final DoseInstanceRepository doseInstanceRepository;

    public DoseLogController(DoseInstanceRepository doseInstanceRepository) {
        this.doseInstanceRepository = doseInstanceRepository;
    }

    @PostMapping("/{id}/taken")
    @Transactional
    public String markTaken(@PathVariable Long id) {
        DoseInstance di = doseInstanceRepository.findById(id).orElse(null);
        if (di != null) {
            di.setStatus(DoseStatus.TAKEN);
        }
        return "redirect:/";
    }

    @PostMapping("/{id}/skipped")
    @Transactional
    public String markSkipped(@PathVariable Long id) {
        DoseInstance di = doseInstanceRepository.findById(id).orElse(null);
        if (di != null) {
            di.setStatus(DoseStatus.SKIPPED);
        }
        return "redirect:/";
    }
}
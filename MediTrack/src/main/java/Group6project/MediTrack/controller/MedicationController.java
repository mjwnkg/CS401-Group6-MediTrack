package Group6project.MediTrack.controller;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Group6project.MediTrack.model.MedScheduleTime;
import Group6project.MediTrack.model.Medication;
import Group6project.MediTrack.repo.MedScheduleTimeRepository;
import Group6project.MediTrack.repo.MedicationRepository;

@Controller
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationRepository medicationRepository;
    private final MedScheduleTimeRepository medScheduleTimeRepository;

    public MedicationController(MedicationRepository medicationRepository,
                                MedScheduleTimeRepository medScheduleTimeRepository) {
        this.medicationRepository = medicationRepository;
        this.medScheduleTimeRepository = medScheduleTimeRepository;
    }

    @GetMapping("/new")
    public String newMedicationForm() {
        return "med_new";
    }

    @PostMapping
    public String createMedication(@RequestParam String name,
                                   @RequestParam(required = false) String dosage,
                                   @RequestParam(required = false) String instructions,
                                   @RequestParam String times,
                                   Model model) {

        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "Medication name is required.");
            return "med_new";
        }

        Medication med = new Medication(name.trim(),
                (dosage == null ? null : dosage.trim()),
                (instructions == null ? null : instructions.trim()));
        med = medicationRepository.save(med);

        //time parse, expects comma in middle
        String[] parts = times.split(",");
        int added = 0;

        for (String p : parts) {
            String token = p.trim();
            if (token.isEmpty()) continue;

            try {
                LocalTime t = LocalTime.parse(token);
                medScheduleTimeRepository.save(new MedScheduleTime(med, t));
                added++;
            } catch (DateTimeParseException ex) {
                //token validation here later, skip for now
            }
        }

        if (added == 0) {
            //validate time was entered correctly, delete if not and show error
            medicationRepository.deleteById(med.getId());
            model.addAttribute("error", "Please enter at least one valid time (HH:MM), e.g., 08:00,20:00");
            return "med_new";
        }

        return "redirect:/";
    }
}
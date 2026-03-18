package Group6project.MediTrack.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Group6project.MediTrack.model.DoseInstance;
import Group6project.MediTrack.repo.DoseInstanceRepository;
import Group6project.MediTrack.service.ScheduleService;

@Controller
public class DashboardController {

    private final ScheduleService scheduleService;
    private final DoseInstanceRepository doseInstanceRepository;

    public DashboardController(ScheduleService scheduleService,
                               DoseInstanceRepository doseInstanceRepository) {
        this.scheduleService = scheduleService;
        this.doseInstanceRepository = doseInstanceRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        LocalDate today = LocalDate.now();

        scheduleService.generateDoseInstancesIfMissing(today);

        List<DoseInstance> doses = doseInstanceRepository.findByDoseDateOrderByScheduledTimeAsc(today);
        model.addAttribute("today", today);
        model.addAttribute("doses", doses);

        return "dashboard";
    }
}
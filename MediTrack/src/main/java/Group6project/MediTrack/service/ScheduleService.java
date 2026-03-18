package Group6project.MediTrack.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Group6project.MediTrack.model.DoseInstance;
import Group6project.MediTrack.model.MedScheduleTime;
import Group6project.MediTrack.model.Medication;
import Group6project.MediTrack.repo.DoseInstanceRepository;
import Group6project.MediTrack.repo.MedScheduleTimeRepository;
import Group6project.MediTrack.repo.MedicationRepository;

@Service
public class ScheduleService {

    private final MedicationRepository medicationRepository;
    private final MedScheduleTimeRepository medScheduleTimeRepository;
    private final DoseInstanceRepository doseInstanceRepository;

    public ScheduleService(
            MedicationRepository medicationRepository,
            MedScheduleTimeRepository medScheduleTimeRepository,
            DoseInstanceRepository doseInstanceRepository
    ) {
        this.medicationRepository = medicationRepository;
        this.medScheduleTimeRepository = medScheduleTimeRepository;
        this.doseInstanceRepository = doseInstanceRepository;
    }

    @Transactional
    public void generateDoseInstancesIfMissing(LocalDate date) {
        List<Medication> meds = medicationRepository.findByActiveTrueOrderByNameAsc();

        for (Medication med : meds) {
            List<MedScheduleTime> times =
                    medScheduleTimeRepository.findByMedicationIdAndEnabledTrueOrderByTimeOfDayAsc(med.getId());

            for (MedScheduleTime t : times) {
                boolean exists = doseInstanceRepository.existsByMedicationIdAndDoseDateAndScheduledTime(
                        med.getId(), date, t.getTimeOfDay()
                );

                if (!exists) {
                    DoseInstance di = new DoseInstance(med, date, t.getTimeOfDay());
                    doseInstanceRepository.save(di);
                }
            }
        }
    }
}
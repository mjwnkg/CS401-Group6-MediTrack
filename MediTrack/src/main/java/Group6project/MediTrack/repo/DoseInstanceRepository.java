package Group6project.MediTrack.repo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Group6project.MediTrack.model.DoseInstance;

public interface DoseInstanceRepository extends JpaRepository<DoseInstance, Long> {

    boolean existsByMedicationIdAndDoseDateAndScheduledTime(
            Long medicationId, LocalDate doseDate, LocalTime scheduledTime
    );

    List<DoseInstance> findByDoseDateOrderByScheduledTimeAsc(LocalDate doseDate);
}
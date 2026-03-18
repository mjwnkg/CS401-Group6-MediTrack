package Group6project.MediTrack.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Group6project.MediTrack.model.MedScheduleTime;

public interface MedScheduleTimeRepository extends JpaRepository<MedScheduleTime, Long> {
    List<MedScheduleTime> findByMedicationIdAndEnabledTrueOrderByTimeOfDayAsc(Long medicationId);
}
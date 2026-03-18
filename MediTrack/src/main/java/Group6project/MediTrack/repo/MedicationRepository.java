package Group6project.MediTrack.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Group6project.MediTrack.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByActiveTrueOrderByNameAsc();
}
package Group6project.MediTrack.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "med_schedule_time",
    uniqueConstraints = @UniqueConstraint(columnNames = {"med_id", "time_of_day"})
)
public class MedScheduleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "med_id", nullable = false)
    private Medication medication;

    @Column(name = "time_of_day", nullable = false)
    private LocalTime timeOfDay;

    @Column(nullable = false)
    private boolean enabled = true;

    public MedScheduleTime() {}

    public MedScheduleTime(Medication medication, LocalTime timeOfDay) {
        this.medication = medication;
        this.timeOfDay = timeOfDay;
        this.enabled = true;
    }

    public Long getId() { return id; }

    public Medication getMedication() { return medication; }
    public void setMedication(Medication medication) { this.medication = medication; }

    public LocalTime getTimeOfDay() { return timeOfDay; }
    public void setTimeOfDay(LocalTime timeOfDay) { this.timeOfDay = timeOfDay; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
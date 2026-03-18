package Group6project.MediTrack.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    name = "dose_instance",
    uniqueConstraints = @UniqueConstraint(columnNames = {"med_id", "dose_date", "scheduled_time"})
)
public class DoseInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "med_id", nullable = false)
    private Medication medication;

    @Column(name = "dose_date", nullable = false)
    private LocalDate doseDate;

    @Column(name = "scheduled_time", nullable = false)
    private LocalTime scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoseStatus status = DoseStatus.PENDING;

    public DoseInstance() {}

    public DoseInstance(Medication medication, LocalDate doseDate, LocalTime scheduledTime) {
        this.medication = medication;
        this.doseDate = doseDate;
        this.scheduledTime = scheduledTime;
        this.status = DoseStatus.PENDING;
    }

    public Long getId() { return id; }

    public Medication getMedication() { return medication; }
    public void setMedication(Medication medication) { this.medication = medication; }

    public LocalDate getDoseDate() { return doseDate; }
    public void setDoseDate(LocalDate doseDate) { this.doseDate = doseDate; }

    public LocalTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalTime scheduledTime) { this.scheduledTime = scheduledTime; }

    public DoseStatus getStatus() { return status; }
    public void setStatus(DoseStatus status) { this.status = status; }
}
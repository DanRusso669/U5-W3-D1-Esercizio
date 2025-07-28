package danrusso.U5_W2_Progetto_Settimanale.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private UUID reservationId;
    @ManyToOne
    @JoinColumn(name = "journey_id")
    private Journey journey;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDate date;
    private String notes;

    public Reservation() {
    }

    public Reservation(Journey journey, Employee employee, LocalDate date, String notes) {
        this.journey = journey;
        this.employee = employee;
        this.date = date;
        this.notes = notes;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", journey=" + journey +
                ", employee=" + employee +
                ", date=" + date +
                ", notes='" + notes + '\'' +
                '}';
    }
}

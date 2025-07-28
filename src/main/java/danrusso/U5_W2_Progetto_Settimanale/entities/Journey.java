package danrusso.U5_W2_Progetto_Settimanale.entities;

import danrusso.U5_W2_Progetto_Settimanale.enums.JourneyType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "journeys")
public class Journey {
    @Id
    @GeneratedValue
    private UUID journeyId;
    private String destination;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private JourneyType status;

    public Journey() {
    }

    public Journey(String destination, LocalDate date, JourneyType status) {
        this.destination = destination;
        this.date = date;
        this.status = status;
    }

    public UUID getJourneyId() {
        return journeyId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public JourneyType getStatus() {
        return status;
    }

    public void setStatus(JourneyType status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "journeyId=" + journeyId +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}

package danrusso.U5_W2_Progetto_Settimanale.repositories;

import danrusso.U5_W2_Progetto_Settimanale.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query("SELECT r FROM Reservation r WHERE r.employee.employeeId = :id AND r.journey.date = :date")
    public List<Reservation> filterByEmployee(UUID id, LocalDate date);

}

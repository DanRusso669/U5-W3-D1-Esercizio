package danrusso.U5_W2_Progetto_Settimanale.repositories;

import danrusso.U5_W2_Progetto_Settimanale.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, UUID> {
}

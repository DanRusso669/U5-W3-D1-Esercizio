package danrusso.U5_W2_Progetto_Settimanale.services;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.entities.Journey;
import danrusso.U5_W2_Progetto_Settimanale.entities.Reservation;
import danrusso.U5_W2_Progetto_Settimanale.enums.JourneyType;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.BadRequestException;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.NotFoundException;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.ToManyReservationsException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewReservationDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewUserReservationDTO;
import danrusso.U5_W2_Progetto_Settimanale.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JourneyService journeyService;

    public Page<Reservation> findAll(int pageNum, int pageSize, String sortBy) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return this.reservationRepository.findAll(pageable);
    }

    public Reservation saveReservation(NewUserReservationDTO payload, Employee currentEmployee) {
        Journey foundJou = this.journeyService.findById(payload.journeyId());
        if (foundJou.getStatus().equals(JourneyType.COMPLETED))
            throw new BadRequestException("You cannot book a completed trip.");
        List<Reservation> foundList = this.reservationRepository.filterByEmployee(currentEmployee.getEmployeeId(), foundJou.getDate());
        if (!foundList.isEmpty()) throw new ToManyReservationsException("You already have a booking for that date.");

        Reservation newReser = new Reservation(foundJou, currentEmployee, LocalDate.now(), payload.notes());

        Reservation savedReser = this.reservationRepository.save(newReser);
        System.out.println("Reservation with id " + savedReser.getReservationId() + " saved successfully.");
        return savedReser;
    }

    public Reservation findById(UUID id) {
        return this.reservationRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Reservation"));
    }

    public Reservation findByIdAndUpdate(UUID id, NewReservationDTO payload) {
        Reservation found = this.findById(id);
        Employee foundEmp = this.employeeService.findById(payload.employeeId());
        Journey foundJou = this.journeyService.findById(payload.journeyId());
        if (foundJou.getStatus().equals(JourneyType.COMPLETED))
            throw new BadRequestException("You cannot book a completed trip.");
        List<Reservation> foundList = this.reservationRepository.filterByEmployee(foundEmp.getEmployeeId(), foundJou.getDate());
        if (!foundList.isEmpty()) throw new ToManyReservationsException("You already have a booking for that date.");
        found.setJourney(foundJou);
        found.setNotes(payload.notes());

        Reservation savedReser = this.reservationRepository.save(found);
        System.out.println("Reservation with id " + savedReser.getReservationId() + " updated successfully.");
        return savedReser;
    }

    public void findByIdAndDelete(UUID id) {
        this.reservationRepository.delete(this.findById(id));
    }

}

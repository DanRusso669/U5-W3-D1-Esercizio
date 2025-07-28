package danrusso.U5_W2_Progetto_Settimanale.services;

import danrusso.U5_W2_Progetto_Settimanale.entities.Journey;
import danrusso.U5_W2_Progetto_Settimanale.enums.JourneyType;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.BadRequestException;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.NotFoundException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewJourneyDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewStatusDTO;
import danrusso.U5_W2_Progetto_Settimanale.repositories.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class JourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    public Page<Journey> findAll(int pageNum, int pageSize, String sortBy) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return this.journeyRepository.findAll(pageable);
    }

    public Journey saveJourney(NewJourneyDTO payload) {
        JourneyType status = JourneyType.SCHEDULED;
        if (payload.date().isBefore(LocalDate.now())) status = JourneyType.COMPLETED;

        Journey newJourney = new Journey(payload.destination(), payload.date(), status);
        Journey savedJourney = this.journeyRepository.save(newJourney);
        System.out.println("New journey  with id " + savedJourney.getJourneyId() + " added successfully.");
        return savedJourney;
    }

    public Journey findById(UUID id) {
        return this.journeyRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Journey"));
    }

    public Journey findByIdAndUpdate(UUID id, NewJourneyDTO payload) {
        Journey found = this.findById(id);
        JourneyType status = JourneyType.SCHEDULED;
        if (payload.date().isBefore(LocalDate.now())) status = JourneyType.COMPLETED;

        found.setDate(payload.date());
        found.setDestination(payload.destination());
        found.setStatus(status);

        Journey updatedJourney = this.journeyRepository.save(found);
        System.out.println("Journey with id " + found.getJourneyId() + " updated successfully.");
        return updatedJourney;

    }

    public void findByIdAndDelete(UUID id) {
        this.journeyRepository.delete(this.findById(id));
    }

    public Journey findByIdAndChangeStatus(UUID id, NewStatusDTO newStatus) {
        Journey found = this.findById(id);
        if (newStatus.status().equalsIgnoreCase("completed")) {
            if (found.getStatus() == JourneyType.COMPLETED)
                throw new BadRequestException("Status of journey with id " + found.getJourneyId() + " is already set to COMPLETED.");
            if (found.getDate().isAfter(LocalDate.now()))
                throw new BadRequestException("A future journey cannot be completed.");
            found.setStatus(JourneyType.COMPLETED);
        } else if (newStatus.status().equalsIgnoreCase("scheduled")) {
            if (found.getStatus() == JourneyType.SCHEDULED)
                throw new BadRequestException("Status of journey with id " + found.getJourneyId() + " is already set to SCHEDULED.");
            if (found.getDate().isBefore(LocalDate.now()))
                throw new BadRequestException("A past journey cannot be scheduled.");
            found.setStatus(JourneyType.SCHEDULED);
        } else {
            throw new BadRequestException("Bad Request format.");
        }
        Journey updatedJourney = this.journeyRepository.save(found);
        System.out.println("Status of journey with id " + found.getJourneyId() + " updated successfully.");
        return updatedJourney;
    }
}

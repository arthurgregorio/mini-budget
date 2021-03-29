package br.eti.arthurgregorio.minibudget.model.services;

import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.repositories.MovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovementRegistrationService {

    private final MovementRepository movementRepository;

    public MovementRegistrationService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Transactional
    public Movement save(Movement Movement) {

        // TODO prevent saving with paid status
        // TODO prevent saving with value less than 1

        return this.movementRepository.save(Movement);
    }

    @Transactional
    public Movement update(Movement Movement) {

        // TODO prevent saving with paid status
        // TODO prevent saving with value less than 1

        return this.movementRepository.save(Movement);
    }

    @Transactional
    public void delete(Movement Movement) {
        this.movementRepository.delete(Movement);
    }
}

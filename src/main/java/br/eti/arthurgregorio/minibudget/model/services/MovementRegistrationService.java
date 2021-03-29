package br.eti.arthurgregorio.minibudget.model.services;

import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.repositories.MovementRepository;
import br.eti.arthurgregorio.minibudget.model.validation.movement.MovementValidatorWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovementRegistrationService {

    private final MovementRepository movementRepository;
    private final MovementValidatorWrapper validatorWrapper;

    public MovementRegistrationService(MovementRepository movementRepository, MovementValidatorWrapper validatorWrapper) {
        this.movementRepository = movementRepository;
        this.validatorWrapper = validatorWrapper;
    }

    @Transactional
    public Movement save(Movement Movement) {
        this.validatorWrapper.validateBeforeSave(Movement);
        return this.movementRepository.save(Movement);
    }

    @Transactional
    public Movement update(Movement Movement) {
        this.validatorWrapper.validateBeforeSave(Movement);
        return this.movementRepository.save(Movement);
    }

    @Transactional
    public void delete(Movement Movement) {
        this.movementRepository.delete(Movement);
    }
}

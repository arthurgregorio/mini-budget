package br.eti.arthurgregorio.minibudget.model.validation.movement;

import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MovementValidatorWrapper {

    private final Set<MovementSavingValidator> savingValidators;

    public MovementValidatorWrapper(Set<MovementSavingValidator> savingValidators) {
        this.savingValidators = savingValidators;
    }

    public void validateBeforeSave(Movement movement) {
        this.savingValidators.forEach(validator -> validator.validate(movement));
    }
}

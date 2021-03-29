package br.eti.arthurgregorio.minibudget.model.validation.movement;

import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class StateValidator implements MovementSavingValidator {

    @Override
    public void validate(Movement object) {
        if (object.getState() != Movement.State.OPEN) {
            throw new BusinessException("movement.error.state-should-be-open",
                    "When saving, movement state should be equal to OPEN, received [%s]", object.getState());
        }
    }
}

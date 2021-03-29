package br.eti.arthurgregorio.minibudget.model.validation.movement;

import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValueValidator implements MovementSavingValidator {

    @Override
    public void validate(Movement object) {
        if (object.getValue().compareTo(BigDecimal.ZERO) < 1) {
            throw new BusinessException("movement.error.value-lt-or-eq-zero",
                    "Movement value should be >= 0, received [%s]", object.getValue());
        }
    }
}

package br.eti.arthurgregorio.minibudget.validation;

import br.eti.arthurgregorio.minibudget.AbstractTest;
import br.eti.arthurgregorio.minibudget.model.entities.Movement;
import br.eti.arthurgregorio.minibudget.model.exceptions.BusinessException;
import br.eti.arthurgregorio.minibudget.model.validation.movement.MovementValidatorWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MovementValidatorsTest extends AbstractTest {

    @Autowired
    private MovementValidatorWrapper validatorWrapper;

    @Test
    void shouldFailWhenValueIsNegative() {

        final var movement = new Movement();
        movement.setValue(BigDecimal.TEN.negate());

        assertThatThrownBy(() -> this.validatorWrapper.validateBeforeSave(movement))
                .isInstanceOf(BusinessException.class)
                .hasMessage("movement.error.value-lt-or-eq-zero");
    }

    @Test
    void shouldFailWhenValueIsZero() {

        final var movement = new Movement();
        movement.setValue(BigDecimal.ZERO);

        assertThatThrownBy(() -> this.validatorWrapper.validateBeforeSave(movement))
                .isInstanceOf(BusinessException.class)
                .hasMessage("movement.error.value-lt-or-eq-zero");
    }

    @Test
    void shouldFailWhenStateIsNotOpen() {

        final var movement = new Movement();
        movement.setState(Movement.State.PAID);

        assertThatThrownBy(() -> this.validatorWrapper.validateBeforeSave(movement))
                .isInstanceOf(BusinessException.class)
                .hasMessage("movement.error.state-should-be-open");
    }
}

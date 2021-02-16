package br.eti.arthurgregorio.minibudget.application.controllers.advice;

import br.eti.arthurgregorio.minibudget.application.validators.message.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static br.eti.arthurgregorio.minibudget.application.validators.message.ValidationErrorResponse.Violation;

@ControllerAdvice
public class ValidationHandlerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException exception) {

        final ValidationErrorResponse error = new ValidationErrorResponse();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            error.add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return error;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        final ValidationErrorResponse response = new ValidationErrorResponse();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            response.add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return response;
    }
}

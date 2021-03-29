package br.eti.arthurgregorio.minibudget.application.controllers.advice;

import br.eti.arthurgregorio.minibudget.application.validators.message.ValidationErrorResponse;
import br.eti.arthurgregorio.minibudget.model.exceptions.BusinessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

import static br.eti.arthurgregorio.minibudget.application.validators.message.ValidationErrorResponse.Violation;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    void handle(HttpServletResponse response, RuntimeException exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    Violation handle(BusinessException exception) throws IOException {
        return new Violation(exception.getMessage(), exception.getDetail());
    }

    // TODO improve the integrity violation handling
    @ExceptionHandler(DataIntegrityViolationException.class)
    void handle(HttpServletResponse response, DataIntegrityViolationException exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    ValidationErrorResponse handle(ConstraintViolationException exception) {

        final ValidationErrorResponse error = new ValidationErrorResponse();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            error.add(new ValidationErrorResponse.Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return error;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ValidationErrorResponse handle(MethodArgumentNotValidException exception) {

        final ValidationErrorResponse response = new ValidationErrorResponse();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            response.add(new ValidationErrorResponse.Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return response;
    }
}
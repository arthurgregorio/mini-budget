package br.eti.arthurgregorio.minibudget.application.controllers.advice;

import br.eti.arthurgregorio.minibudget.model.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    void handle(HttpServletResponse response, RuntimeException exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(
            BusinessException.class
    )
    void handle(HttpServletResponse response, BusinessException exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}
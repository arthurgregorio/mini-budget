package br.eti.arthurgregorio.minibudget.model.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends RuntimeException {

    protected Object[] parameters;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, Object... parameters) {
        super(message.formatted(parameters));
    }
}

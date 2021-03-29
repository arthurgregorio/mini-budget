package br.eti.arthurgregorio.minibudget.model.exceptions;

import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    protected String detail;
    @Getter
    protected Object[] parameters;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String detail, Object... parameters) {
        super(message);
        this.parameters = parameters;
        this.detail = detail.formatted(parameters);
    }

    public BusinessException(String message, String detail, Throwable cause, Object... parameters) {
        super(message, cause);
        this.parameters = parameters;
        this.detail = detail.formatted(parameters);
    }
}

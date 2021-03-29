package br.eti.arthurgregorio.minibudget.model.validation;

public interface BusinessValidator<T> {

    void validate(T object);
}

package br.eti.arthurgregorio.minibudget.model.exceptions;

public class ClassificationNotFound extends BusinessException {

    public ClassificationNotFound(String contactId) {
        super("Can't find classification with external id [%s]".formatted(contactId));
    }
}

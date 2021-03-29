package br.eti.arthurgregorio.minibudget.model.exceptions;

public class ContactNotFound extends BusinessException {

    public ContactNotFound(String contactId) {
        super("Can't find contact with id = [%s]".formatted(contactId));
    }
}

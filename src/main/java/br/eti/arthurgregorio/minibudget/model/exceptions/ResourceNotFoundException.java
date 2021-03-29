package br.eti.arthurgregorio.minibudget.model.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(UUID externalId) {
        super("Cannot find any resource with id = [%s]", externalId.toString());
    }
}

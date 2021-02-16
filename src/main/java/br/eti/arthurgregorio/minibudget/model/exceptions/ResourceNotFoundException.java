package br.eti.arthurgregorio.minibudget.model.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(UUID externalId) {
        super("Resource with id = [%s] can't be found", externalId.toString());
    }
}

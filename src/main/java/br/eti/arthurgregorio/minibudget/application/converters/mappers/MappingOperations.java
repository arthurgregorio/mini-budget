package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.BasePayload;
import br.eti.arthurgregorio.minibudget.model.entities.PersistentEntity;
import org.mapstruct.*;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MappingOperations {

    @Mapping(source = "externalId", target = "id")
    BasePayload defaultFromModel(PersistentEntity model);

    @InheritInverseConfiguration
    @Mapping(source = "id", target = "externalId")
    PersistentEntity defaultToModel(BasePayload payload);
}

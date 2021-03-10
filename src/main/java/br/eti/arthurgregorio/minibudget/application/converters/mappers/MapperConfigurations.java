package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.BasePayload;
import br.eti.arthurgregorio.minibudget.model.entities.PersistentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapperConfigurations {

    @Mapping(source = "externalId", target = "id")
    BasePayload defaultFromModel(PersistentEntity model);

    @InheritInverseConfiguration
    @Mapping(source = "id", target = "externalId")
    PersistentEntity defaultToModel(BasePayload payload);
}

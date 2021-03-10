package br.eti.arthurgregorio.minibudget.application.converters.mappers;

import br.eti.arthurgregorio.minibudget.application.payloads.BasePayload;
import br.eti.arthurgregorio.minibudget.model.entities.PersistentEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfigurations.class)
public interface MappingOperations<T extends PersistentEntity, V extends BasePayload> {

    V defaultFromModel(T model);

    T defaultToModel(V payload);
}

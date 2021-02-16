package br.eti.arthurgregorio.minibudget.application.converters.mappers;

public interface MappingOperations<T, V> {

    V toPayload(T model);

    T toModel(V payload);
}

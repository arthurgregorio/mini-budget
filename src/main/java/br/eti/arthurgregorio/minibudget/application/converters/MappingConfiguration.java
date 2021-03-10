package br.eti.arthurgregorio.minibudget.application.converters;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.converter.ConversionServiceAdapterGenerator;

@MapperConfig(componentModel = "spring", uses = ConversionServiceAdapterGenerator.class)
public interface MappingConfiguration {
}

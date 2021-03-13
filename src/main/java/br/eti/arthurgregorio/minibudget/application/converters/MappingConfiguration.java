package br.eti.arthurgregorio.minibudget.application.converters;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.converter.ConversionServiceAdapter;

@MapperConfig(componentModel = "spring", uses = ConversionServiceAdapter.class)
public interface MappingConfiguration {
}

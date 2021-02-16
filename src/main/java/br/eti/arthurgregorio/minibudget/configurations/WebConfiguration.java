package br.eti.arthurgregorio.minibudget.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final List<Formatter<?>> formatters;
    private final List<Converter<?, ?>> converters;

    @Override
    public void addFormatters(final FormatterRegistry formatterRegistry) {
        this.formatters.forEach(formatterRegistry::addFormatter);
        this.converters.forEach(formatterRegistry::addConverter);
    }
}
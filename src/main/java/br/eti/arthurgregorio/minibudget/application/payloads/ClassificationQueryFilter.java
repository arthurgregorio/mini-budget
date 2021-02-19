package br.eti.arthurgregorio.minibudget.application.payloads;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ClassificationQueryFilter {

    @Getter
    private final String name;

    public ClassificationQueryFilter(final String name) {
        this.name = name;
    }
}

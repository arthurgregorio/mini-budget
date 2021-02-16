package br.eti.arthurgregorio.minibudget.application.payloads;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ContactQueryFilter {

    @Getter
    private final String name;
    @Getter
    private final String email;

    public ContactQueryFilter(final String name, final String email) {
        this.name = name;
        this.email = email;
    }
}

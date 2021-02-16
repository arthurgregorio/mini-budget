package br.eti.arthurgregorio.minibudget.application.validators.message;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    @Getter
    private final List<Violation> violations;

    public ValidationErrorResponse() {
        this.violations = new ArrayList<>();
    }

    public void add(Violation violation) {
        this.violations.add(violation);
    }

    public static class Violation {

        @Getter
        private final String property;
        @Getter
        private final String message;

        public Violation(String property, String message) {
            this.property = property;
            this.message = message;
        }
    }
}

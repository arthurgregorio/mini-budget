package br.eti.arthurgregorio.minibudget.application.validators;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String attribute;
    private Class<?> domainClass;

    @Override
    public void initialize(Unique annotation) {
        this.attribute = annotation.attribute();
        this.domainClass = annotation.domainClass();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        final String sql = "select 1 from %s where %s = :value";

        final Query query = this.entityManager.createQuery(sql.formatted(this.domainClass.getName(), this.attribute));
        query.setParameter("value", object);

        final var result = query.getResultList();

        return result.isEmpty();
    }
}

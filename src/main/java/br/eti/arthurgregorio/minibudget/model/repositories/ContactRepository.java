package br.eti.arthurgregorio.minibudget.model.repositories;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactQueryFilter;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends DefaultRepository<Contact> {

    @Query("from Contact c " +
            "where (:#{#filter.name} is null or c.name like :#{#filter.name}) " +
            "and (:#{#filter.email} is null or c.email like :#{#filter.email}) ")
    Page<Contact> findByFilter(@Param("filter") ContactQueryFilter filter, Pageable pageable);
}

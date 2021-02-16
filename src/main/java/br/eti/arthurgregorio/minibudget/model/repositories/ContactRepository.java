package br.eti.arthurgregorio.minibudget.model.repositories;

import br.eti.arthurgregorio.minibudget.application.payloads.ContactQueryFilter;
import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends DefaultRepository<Contact> {

    @Query("from Contact c " +
            "where (:#{#filter.name} is null or lower(c.name) like lower(:#{#filter.name}))" +
            "and (:#{#filter.email} is null or lower(c.email) like lower(:#{#filter.email})) ")
    Page<Contact> findByFilter(@Param("filter") ContactQueryFilter filter, Pageable pageable);

    @Query("from Contact c " +
            "where (:#{#email} is null or lower(c.email) like lower(:#{#email}))")
    List<Contact> findByEmail(@Param("email") String email);
}

package br.eti.arthurgregorio.minibudget.model.repositories;

import br.eti.arthurgregorio.minibudget.application.payloads.ClassificationQueryFilter;
import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends DefaultRepository<Classification> {

    @Query("from Classification c " +
            "where (:#{#filter.name} is null or lower(c.name) like lower(:#{#filter.name}))")
    Page<Classification> findByFilter(@Param("filter") ClassificationQueryFilter filter, Pageable pageable);
}

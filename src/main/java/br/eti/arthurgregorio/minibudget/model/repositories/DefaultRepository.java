package br.eti.arthurgregorio.minibudget.model.repositories;

import br.eti.arthurgregorio.minibudget.model.entities.IPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface DefaultRepository<T extends IPersistentEntity<? extends Serializable>> extends JpaRepository<T, Long> {

    Optional<T> findByExternalId(UUID uuid);
}

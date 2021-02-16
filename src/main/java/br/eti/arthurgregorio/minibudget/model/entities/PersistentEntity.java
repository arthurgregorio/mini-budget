package br.eti.arthurgregorio.minibudget.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class PersistentEntity implements IPersistentEntity<Long> {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    @Getter
    @Column(name = "external_id", length = 36, updatable = false, unique = true)
    private UUID externalId;
    @Getter
    @CreatedDate
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Getter
    @LastModifiedDate
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Version
    private short version;

    @Override
    public boolean isSaved() {
        return this.id != null && this.id != 0;
    }

    @PrePersist
    protected void onPersist() {
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID();
        }
    }
}

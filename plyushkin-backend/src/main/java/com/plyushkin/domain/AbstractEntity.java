package com.plyushkin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public abstract class AbstractEntity<T extends AbstractEntity<T>> extends AbstractAggregateRoot<T> {
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "created_at", updatable = false))
    )
    @ToString.Include
    @CreatedDate
    private Instant createdAt;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "updated_at"))
    )
    @ToString.Include
    @LastModifiedBy
    private Instant updatedAt;
}

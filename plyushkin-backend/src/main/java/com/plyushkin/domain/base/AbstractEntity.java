package com.plyushkin.domain.base;

import com.plyushkin.domain.user.User;
import com.plyushkin.domain.value.ID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@NoArgsConstructor(access = PROTECTED)
public abstract class AbstractEntity<T extends AbstractEntity<T>> extends AbstractAggregateRoot<T> {
    @AttributeOverride(name = "value", column = @Column(name = "created_at", updatable = false))
    @ToString.Include
    @CreatedDate
    protected Instant createdAt;

    @AttributeOverride(name = "value", column = @Column(name = "updated_at"))
    @ToString.Include
    @LastModifiedDate
    protected Instant updatedAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", updatable = false))
    @CreatedBy
    protected ID<User> createdBy;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "updated_by"))
    @LastModifiedBy
    protected ID<User> updatedBy;

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}

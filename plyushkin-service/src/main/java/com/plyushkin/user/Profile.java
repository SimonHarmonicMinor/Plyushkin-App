package com.plyushkin.user;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
@NoArgsConstructor(access = PROTECTED)
public class Profile {
  @Enumerated(STRING)
  @Column(updatable = false)
  protected ProfileType type;

  @Column(updatable = false)
  protected String id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id", updatable = false)
  protected User user;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long pk;

  protected Profile(ProfileType type, String id, User user) {
    this.type = type;
    this.id = id;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Profile p) {
      return pk != null && Objects.equals(pk, p.pk);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

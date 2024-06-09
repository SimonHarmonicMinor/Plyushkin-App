package com.plyushkin.user;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
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

  @Id
  protected String id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id", updatable = false)
  protected User user;

  protected Profile(String id, User user) {
    this.id = id;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Profile p) {
      return Objects.equals(id, p.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}

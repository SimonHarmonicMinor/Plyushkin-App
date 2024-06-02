package com.plyushkin.user;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = PRIVATE)
public class User {

  @EmbeddedId
  private UserId id;
}

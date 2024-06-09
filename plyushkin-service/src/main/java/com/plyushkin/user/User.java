package com.plyushkin.user;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "auth", name = "users")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
public class User {

  @EmbeddedId
  private UserId id;

  @OneToMany(mappedBy = "user")
  private List<Profile> profiles;
}

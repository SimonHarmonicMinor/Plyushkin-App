package com.plyushkin.user;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth.google_profile")
@NoArgsConstructor(access = PROTECTED)
public class GoogleProfile extends Profile {

  protected GoogleProfile(String id, User user) {
    super(id, user);
  }
}

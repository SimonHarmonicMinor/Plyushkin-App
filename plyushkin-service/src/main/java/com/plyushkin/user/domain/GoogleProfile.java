package com.plyushkin.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "auth", name = "google_profile")
@NoArgsConstructor(access = PROTECTED)
public class GoogleProfile extends Profile {

    protected GoogleProfile(String id, User user) {
        super(id, user);
    }
}

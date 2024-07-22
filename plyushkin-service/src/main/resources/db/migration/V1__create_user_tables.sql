CREATE SCHEMA auth;

CREATE TABLE auth.users
(
    id BIGINT PRIMARY KEY
);

CREATE TABLE auth.google_profile
(
    id      BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES auth.users (id)
);
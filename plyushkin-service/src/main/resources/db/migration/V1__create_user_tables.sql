CREATE SCHEMA auth;

CREATE TABLE auth.users
(
    id BIGINT PRIMARY KEY
);

CREATE TABLE auth.google_profile
(
    id      VARCHAR(200) PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES auth.users (id)
);
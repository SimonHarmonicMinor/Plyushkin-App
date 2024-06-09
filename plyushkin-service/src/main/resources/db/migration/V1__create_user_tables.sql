CREATE SCHEMA auth;

CREATE TABLE auth.users
(
    id VARCHAR(50) PRIMARY KEY
);

CREATE TABLE auth.google_profile
(
    id      VARCHAR(200) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL REFERENCES auth.users (id)
);
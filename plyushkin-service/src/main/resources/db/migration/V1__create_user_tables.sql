CREATE SCHEMA auth;

CREATE DOMAIN auth.USER_ID AS VARCHAR(50);

CREATE TABLE auth.users
(
    id auth.USER_ID PRIMARY KEY
);

CREATE TABLE auth.google_profile
(
    id      VARCHAR(200) PRIMARY KEY,
    user_id auth.USER_ID NOT NULL REFERENCES auth.users (id)
);
CREATE SCHEMA wallet;

CREATE TABLE wallet.wallet
(
    id         BIGINT PRIMARY KEY,
    name       VARCHAR(200) NOT NULL,
    created_by BIGINT       NOT NULL REFERENCES auth.users (id)
);
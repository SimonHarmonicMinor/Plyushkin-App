CREATE SCHEMA wallet;

CREATE TABLE wallet.wallet
(
    id         VARCHAR(50) PRIMARY KEY,
    name       VARCHAR(200) NOT NULL,
    currency   VARCHAR(50)  NOT NULL,
    created_by VARCHAR(50) NOT NULL REFERENCES auth.users (id)
);
CREATE SCHEMA wallet;

CREATE DOMAIN wallet.WALLET_ID AS VARCHAR(50);

CREATE TABLE wallet.wallet
(
    id         wallet.WALLET_ID PRIMARY KEY,
    name       VARCHAR(200) NOT NULL,
    currency   VARCHAR(50)  NOT NULL,
    created_by auth.USER_ID NOT NULL REFERENCES auth.users (id)
);
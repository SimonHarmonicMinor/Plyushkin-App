CREATE SCHEMA budget;

CREATE TABLE budget.expense_category
(
    pk             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number         BIGINT       NOT NULL,
    name           VARCHAR(200) NOT NULL,
    wallet_id      BIGINT       NOT NULL REFERENCES wallet.wallet (id),
    who_created_id BIGINT       NOT NULL REFERENCES auth.users (id),
    parent_id      BIGINT REFERENCES budget.expense_category (pk),
    UNIQUE (wallet_id, name),
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.expense_record
(
    pk          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number      BIGINT          NOT NULL,
    wallet_id   BIGINT          NOT NULL REFERENCES wallet.wallet (id),
    who_did_id  BIGINT          NOT NULL REFERENCES auth.users (id),
    date        DATE            NOT NULL,
    currency    VARCHAR(50)     NOT NULL,
    amount      NUMERIC(33, 18) NOT NULL,
    category_id BIGINT          NOT NULL REFERENCES budget.expense_category (pk),
    comment     VARCHAR(500)    NOT NULL,
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.income_category
(
    pk             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number         BIGINT       NOT NULL,
    name           VARCHAR(200) NOT NULL,
    wallet_id      BIGINT       NOT NULL REFERENCES wallet.wallet (id),
    who_created_id BIGINT       NOT NULL REFERENCES auth.users (id),
    parent_id      BIGINT REFERENCES budget.income_category (pk),
    UNIQUE (wallet_id, name),
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.income_record
(
    pk          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number      BIGINT          NOT NULL,
    wallet_id   BIGINT          NOT NULL REFERENCES wallet.wallet (id),
    who_did_id  BIGINT          NOT NULL REFERENCES auth.users (id),
    date        DATE            NOT NULL,
    currency    VARCHAR(50)     NOT NULL,
    amount      NUMERIC(33, 18) NOT NULL,
    category_id BIGINT          NOT NULL REFERENCES budget.income_category (pk),
    comment     VARCHAR(500)    NOT NULL,
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.deposit
(
    pk             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number         BIGINT          NOT NULL,
    name           VARCHAR(200)    NOT NULL,
    comment        VARCHAR(500)    NOT NULL,
    currency       VARCHAR(50)     NOT NULL,
    wallet_id      BIGINT          NOT NULL REFERENCES wallet.wallet (id),
    initial_amount NUMERIC(33, 18) NOT NULL,
    initial_date   DATE            NOT NULL,
    closed_amount  NUMERIC(33, 18),
    closed_date    DATE
)
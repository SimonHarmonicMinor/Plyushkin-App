CREATE SCHEMA budget;

CREATE TABLE budget.expense_note_category
(
    pk             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number         BIGINT       NOT NULL,
    name           VARCHAR(200) NOT NULL,
    wallet_id      VARCHAR(50)  NOT NULL REFERENCES wallet.wallet (id),
    who_created_id VARCHAR(50)  NOT NULL REFERENCES auth.users (id),
    parent_id      BIGINT REFERENCES budget.expense_note_category (pk),
    UNIQUE (wallet_id, name),
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.expense_note
(
    pk          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number      BIGINT          NOT NULL,
    wallet_id   VARCHAR(50)     NOT NULL REFERENCES wallet.wallet (id),
    who_did_id  VARCHAR(50)     NOT NULL REFERENCES auth.users (id),
    date        DATE            NOT NULL,
    amount      NUMERIC(33, 18) NOT NULL,
    category_id BIGINT          NOT NULL REFERENCES budget.expense_note_category (pk),
    comment     VARCHAR(500)    NOT NULL,
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.income_note_category
(
    pk             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number         BIGINT       NOT NULL,
    name           VARCHAR(200) NOT NULL,
    wallet_id      VARCHAR(50)  NOT NULL REFERENCES wallet.wallet (id),
    who_created_id VARCHAR(50)  NOT NULL REFERENCES auth.users (id),
    parent_id      BIGINT REFERENCES budget.income_note_category (pk),
    UNIQUE (wallet_id, name),
    UNIQUE (wallet_id, number)
);

CREATE TABLE budget.income_note
(
    pk          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number      BIGINT          NOT NULL,
    wallet_id   VARCHAR(50)     NOT NULL REFERENCES wallet.wallet (id),
    who_did_id  VARCHAR(50)     NOT NULL REFERENCES auth.users (id),
    date        DATE            NOT NULL,
    amount      NUMERIC(33, 18) NOT NULL,
    category_id BIGINT          NOT NULL REFERENCES budget.income_note_category (pk),
    comment     VARCHAR(500)    NOT NULL,
    UNIQUE (wallet_id, number)
);
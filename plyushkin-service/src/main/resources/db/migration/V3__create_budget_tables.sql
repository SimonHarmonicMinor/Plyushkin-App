CREATE SCHEMA budget;

CREATE DOMAIN budget.EXPENSE_NOTE_ID AS VARCHAR(50);

CREATE DOMAIN budget.EXPENSE_NOTE_CATEGORY_ID AS VARCHAR(50);

CREATE DOMAIN budget.INCOME_NOTE_ID AS VARCHAR(50);

CREATE DOMAIN budget.INCOME_NOTE_CATEGORY_ID AS VARCHAR(50);

CREATE TABLE budget.expense_note_category
(
    id             budget.EXPENSE_NOTE_CATEGORY_ID NOT NULL UNIQUE,
    name           VARCHAR(200)                    NOT NULL,
    wallet_id      wallet.WALLET_ID                NOT NULL REFERENCES wallet.wallet (id),
    who_created_id auth.USER_ID                    NOT NULL REFERENCES auth.users (id),
    parent_id      budget.EXPENSE_NOTE_CATEGORY_ID REFERENCES budget.expense_note_category (id),
    UNIQUE (wallet_id, name)
);

CREATE TABLE budget.expense_note
(
    id          budget.EXPENSE_NOTE_ID          NOT NULL UNIQUE,
    wallet_id   wallet.WALLET_ID                NOT NULL REFERENCES wallet.wallet (id),
    who_did_id  auth.USER_ID                    NOT NULL REFERENCES auth.users (id),
    date        DATE                            NOT NULL,
    amount      BIGINT                          NOT NULL,
    category_id budget.EXPENSE_NOTE_CATEGORY_ID NOT NULL REFERENCES budget.expense_note_category (id),
    comment     VARCHAR(500)                    NOT NULL
);

CREATE TABLE budget.income_note_category
(
    id             budget.INCOME_NOTE_CATEGORY_ID NOT NULL UNIQUE,
    name           VARCHAR(200)                   NOT NULL,
    wallet_id      wallet.WALLET_ID               NOT NULL REFERENCES wallet.wallet (id),
    who_created_id auth.USER_ID                   NOT NULL REFERENCES auth.users (id),
    parent_id      budget.INCOME_NOTE_CATEGORY_ID REFERENCES budget.income_note_category (id),
    UNIQUE (wallet_id, name)
);

CREATE TABLE budget.income_note
(
    id          budget.INCOME_NOTE_ID          NOT NULL UNIQUE,
    wallet_id   wallet.WALLET_ID               NOT NULL REFERENCES wallet.wallet (id),
    who_did_id  auth.USER_ID                   NOT NULL REFERENCES auth.users (id),
    date        DATE                           NOT NULL,
    amount      BIGINT                         NOT NULL,
    category_id budget.INCOME_NOTE_CATEGORY_ID NOT NULL REFERENCES budget.income_note_category (id),
    comment     VARCHAR(500)                   NOT NULL
);
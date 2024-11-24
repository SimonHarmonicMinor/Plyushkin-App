CREATE TYPE currency_type AS ENUM ('RUB', 'DOLLAR');
CREATE TYPE budget_record_type AS ENUM ('DEPOSIT', 'INCOME', 'EXPENSE', 'CURRENCY_SWAP', 'SHARE', 'DIVIDEND');

CREATE TABLE budget_record
(
    type            budget_record_type NOT NULL,
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    currency        currency_type      NOT NULL,
    date            DATE               NOT NULL,
    price           NUMERIC(33, 18)    NOT NULL,
    comment         VARCHAR(300)       NOT NULL,

    -- deposit columns
    closedDate      DATE,

    -- Income/expense columns
    category        VARCHAR(100),

    -- Currency swap columns
    bought_currency currency_type,
    bought_price    NUMERIC(33, 18),

    -- Share columns
    sold_date       DATE,
    sold_price      NUMERIC(33, 18),

    -- Dividend columns
    share_number    BIGINT
)
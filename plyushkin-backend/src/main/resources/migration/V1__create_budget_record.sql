CREATE TABLE budget_record
(
    type                    VARCHAR(50)                     NOT NULL,
    id                      BIGINT PRIMARY KEY,
    wallet_id               BIGINT                          NOT NULL,
    date                    DATE                            NOT NULL,
    comment                 VARCHAR(200)                    NOT NULL,
    currency_id             BIGINT REFERENCES currency (id) NOT NULL,
    amount                  NUMERIC(33, 18)                 NOT NULL,

    -- deposit columns
    deposit_closed_date     DATE,
    deposit_closed_amount   DATE,

    -- income columns
    income_category_id      BIGINT REFERENCES income_category (id),

    -- expense columns
    expense_category_id     BIGINT REFERENCES expense_category (id),

    -- share
    share_company_id        BIGINT REFERENCES company (id),
    share_count             INTEGER,
    share_fee               NUMERIC(33, 18),
    share_operation         VARCHAR(50),

    -- dividend
    dividend_company_id     BIGINT REFERENCES company (id),
    dividend_tax            NUMERIC(33, 18),

    -- currency swap
    swap_bought_amount      NUMERIC(33, 18),
    swap_bought_currency_id BIGINT REFERENCES currency (id)
)
CREATE TABLE wallet
(
    created_by UUID                     NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    id         UUID PRIMARY KEY,
    name       VARCHAR(200)             NOT NULL
);

CREATE TABLE currency
(
    created_by UUID                        NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP WITH TIME ZONE    NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,


    id         UUID PRIMARY KEY,
    wallet_id  UUID REFERENCES wallet (id) NOT NULL,
    name       VARCHAR(200)                NOT NULL
);

CREATE TABLE income_category
(
    created_by UUID                        NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP WITH TIME ZONE    NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    id         UUID PRIMARY KEY,
    wallet_id  UUID REFERENCES wallet (id) NOT NULL,
    name       VARCHAR(200)                NOT NULL
);

CREATE TABLE expense_category
(
    created_by UUID                        NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP WITH TIME ZONE    NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    id         UUID PRIMARY KEY,
    wallet_id  UUID REFERENCES wallet (id) NOT NULL,
    name       VARCHAR(200)                NOT NULL
);

CREATE TABLE company
(
    created_by UUID                        NOT NULL,
    updated_by UUID,
    created_at TIMESTAMP WITH TIME ZONE    NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    id         UUID PRIMARY KEY,
    wallet_id  UUID REFERENCES wallet (id) NOT NULL,
    name       VARCHAR(200)                NOT NULL
);

CREATE TABLE budget_record
(
    created_by              UUID                          NOT NULL,
    updated_by              UUID,
    created_at              TIMESTAMP WITH TIME ZONE      NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE,

    type                    VARCHAR(50)                   NOT NULL,
    id                      UUID PRIMARY KEY,
    wallet_id               UUID REFERENCES wallet (id)   NOT NULL,
    date                    DATE                          NOT NULL,
    comment                 VARCHAR(200)                  NOT NULL,
    currency_id             UUID REFERENCES currency (id) NOT NULL,
    amount                  NUMERIC(33, 18)               NOT NULL,

    -- deposit columns
    deposit_closed_date     DATE,
    deposit_closed_amount   NUMERIC(33, 18),

    -- income columns
    income_category_id      UUID REFERENCES income_category (id),

    -- expense columns
    expense_category_id     UUID REFERENCES expense_category (id),

    -- share
    share_company_id        UUID REFERENCES company (id),
    share_count             INTEGER,
    share_fee               NUMERIC(33, 18),
    share_operation         VARCHAR(50),

    -- dividend
    dividend_company_id     UUID REFERENCES company (id),
    dividend_tax            NUMERIC(33, 18),

    -- currency swap
    swap_bought_amount      NUMERIC(33, 18),
    swap_bought_currency_id UUID REFERENCES currency (id)
)
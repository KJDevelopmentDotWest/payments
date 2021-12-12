CREATE TABLE IF NOT EXISTS public.profile_pictures
(
    id SERIAL NOT NULL,
    name character varying(15) COLLATE pg_catalog."default" NOT NULL,
    path character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT profile_pictures_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.profile_pictures
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.accounts
(
    id SERIAL NOT NULL,
    name character varying(15) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(15) COLLATE pg_catalog."default" NOT NULL,
    profile_picture_id bigint,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT "avatar_id to avatar" FOREIGN KEY (profile_picture_id)
        REFERENCES public.profile_pictures (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.accounts
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.users
(
    id SERIAL NOT NULL,
    login character varying(15) COLLATE pg_catalog."default" NOT NULL,
    password character varying(45) COLLATE pg_catalog."default" NOT NULL,
    account_id bigint,
    is_active boolean,
    role_id bigint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT "account_id to account" FOREIGN KEY (account_id)
        REFERENCES public.accounts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

CREATE INDEX login_index
    ON public.users USING btree
    (login COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.payments
(
    id SERIAL NOT NULL,
    user_id bigint NOT NULL,
    destination_address character varying(45) COLLATE pg_catalog."default",
    price bigint,
    committed boolean,
    "time" character varying(45) COLLATE pg_catalog."default",
    name character varying(45) COLLATE pg_catalog."default",
    CONSTRAINT payments_pkey PRIMARY KEY (id),
    CONSTRAINT "user_id to user id" FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.payments
    OWNER to postgres;

CREATE INDEX user_id_index_payments
    ON public.payments USING btree
    (user_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.credit_cards
(
    id SERIAL NOT NULL,
    name character varying(15) COLLATE pg_catalog."default",
    expire_date character varying(45) COLLATE pg_catalog."default",
    user_id bigint NOT NULL,
    "number" bigint,
    CONSTRAINT credit_cards_pkey PRIMARY KEY (id),
    CONSTRAINT "user_id to user id" FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.credit_cards
    OWNER to postgres;

CREATE INDEX user_id_index_credit_cards
    ON public.credit_cards USING btree
    (user_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.bank_accounts
(
    id SERIAL NOT NULL,
    balance bigint NOT NULL,
    blocked boolean,
    credit_card_id bigint NOT NULL,
    CONSTRAINT bank_accounts_pkey PRIMARY KEY (id),
    CONSTRAINT "credit_card_id to credit card" FOREIGN KEY (credit_card_id)
        REFERENCES public.credit_cards (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.bank_accounts
    OWNER to postgres;
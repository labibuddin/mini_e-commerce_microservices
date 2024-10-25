CREATE TABLE users
(
    id       VARCHAR(100) NOT NULL,
    name     VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role     VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE users
    ADD COLUMN token            VARCHAR(100),
    ADD COLUMN token_expired_at BIGINT;

SELECT * FROM users;

DELETE FROM users WHERE name = 'alfin';

ALTER TABLE users ADD PRIMARY KEY (name);

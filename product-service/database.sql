CREATE TABLE product
(
    id    VARCHAR(100)     NOT NULL,
    name  VARCHAR(100)     NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock INTEGER          NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

SELECT * FROM product;

ALTER TABLE product
    ALTER COLUMN price TYPE DOUBLE PRECISION;

DROP TABLE product;

SELECT * FROM product;

SELECT * FROM product WHERE price >= 2000;


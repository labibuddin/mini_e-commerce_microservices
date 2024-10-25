ALTER TABLE order_ RENAME TO orders;

ALTER TABLE orders ADD COLUMN product_name VARCHAR(100);

SELECT * FROM orders;

DELETE FROM orders WHERE id='cf1c5a72-d1bc-45c6-bed6-63ad770fa94a';
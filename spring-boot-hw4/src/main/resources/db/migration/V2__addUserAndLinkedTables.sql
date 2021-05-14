DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(255) UNIQUE  NOT NULL,
                       password VARCHAR(255) UNIQUE  NOT NULL
);

INSERT INTO users (login, password) VALUES
('login1', 'pass1'),
('login2', 'pass2'),
('login3', 'pass3');

DROP TABLE IF EXISTS users_products;
CREATE TABLE users_products
(
    id         SERIAL PRIMARY KEY,
    user_id    BIGINT,
    product_id BIGINT
);

INSERT INTO users_products (user_id, product_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 5);
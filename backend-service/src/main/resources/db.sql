CREATE DATABASE db-test;
CREATE TABLE contacts
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR,
    email     VARCHAR,
    telephone VARCHAR
);
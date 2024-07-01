CREATE TABLE beach_distance (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO beach_distance (name) VALUES ('Přímo u pláže'), ('Do 5 min'), ('Do 15 min'), ('Více, než 15 min');

CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    beach_dist INT NOT NULL REFERENCES beach_distance(id)
);

CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    price FLOAT8 NOT NULL,
    hotel INT NOT NULL REFERENCES hotels(id),
    persons INT NOT NULL,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL
);

INSERT INTO hotels (name, beach_dist)
VALUES
    ('Hotel Blue Fish', 1),
    ('Antal', 2),
    ('Golden Star', 3),
    ('Divan Cave Hotel', 4);

INSERT INTO trips (price, hotel, persons, date_from, date_to) VALUES(18000.0, 1, 5, '2025-6-6', '2025-6-16');
INSERT INTO trips (price, hotel, persons, date_from, date_to) VALUES(20000.0, 2, 4, '2025-6-17', '2025-6-27');
INSERT INTO trips (price, hotel, persons, date_from, date_to) VALUES(50000.0, 3, 3, '2025-6-28', '2025-7-8');
INSERT INTO trips (price, hotel, persons, date_from, date_to) VALUES(35000.0, 4, 4, '2025-7-9', '2025-7-19');
INSERT INTO trips (price, hotel, persons, date_from, date_to) VALUES(26000.0, 2, 3, '2025-7-20', '2025-7-30');
INSERT INTO trips (price, hotel, persons, date_from, date_to) VALUES(29000.0, 3, 5, '2025-7-31', '2025-8-10');


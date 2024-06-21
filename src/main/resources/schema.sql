CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    price float8 NOT NULL,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL
);

INSERT INTO trips (price, date_from, date_to) VALUES(18000.0, '2025-6-6', '2025-6-16');
INSERT INTO trips (price, date_from, date_to) VALUES(20000.0, '2025-6-17', '2025-6-27');
INSERT INTO trips (price, date_from, date_to) VALUES(50000.0, '2025-6-28', '2025-7-8');
INSERT INTO trips (price, date_from, date_to) VALUES(35000.0, '2025-7-9', '2025-7-19');
INSERT INTO trips (price, date_from, date_to) VALUES(26000.0, '2025-7-20', '2025-7-30');
INSERT INTO trips (price, date_from, date_to) VALUES(29000.0, '2025-7-31', '2025-8-10');


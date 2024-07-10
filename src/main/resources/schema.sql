CREATE TABLE beach_distance (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO beach_distance (name) VALUES ('Přímo u pláže'), ('Do 5 min'), ('Do 15 min'), ('Více, než 15 min');

CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    stars INT NOT NULL,
    beach_dist INT NOT NULL REFERENCES beach_distance(id)
);

CREATE TABLE food_packages (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO food_packages (name) VALUES ('All inclusive'), ('Plná penze'), ('Polopenze'), ('Snídaně'), ('Bez stravy');

CREATE TABLE airports (
    id SERIAL PRIMARY KEY,
    iata VARCHAR NOT NULL,
    name VARCHAR NOT NULL
);

INSERT INTO airports (iata, name)
VALUES
    ('PRG', 'Praha, Letiště Václava Havla'), ('BRQ', 'Brno, Tuřany'),
    ('OSR', 'Ostrava, Letiště Leoše Janáčka'), ('PED', 'Pardubice'),
    ('KLV', 'Karlovy Vary'), ('JCL', 'České Budějovice'), ('VIE', 'Vídeň, Schwechat'),
    ('BTS', 'Bratislava, Letiště Milana Rastislava Štefánika'), ('MUC', 'Mnichov'),
    ('DRS', 'Drážďany'), ('LNZ', 'Linec'), ('FRA', 'Frankfurt'), ('BER', 'Berlín'),
    ('SLD', 'Sliač'), ('TAT', 'Poprad'), ('KSC', 'Košice'), ('PZY', 'Piešťany'),
    ('ILZ', 'Žilina'), ('NUE', 'Norimberk'), ('DUS', 'Düsseldorf'), ('ERF', 'Erfurt'),
    ('LEJ', 'Lipsko'), ('KTW', 'Katovice'), ('SZG', 'Salzburg'), ('STR', 'Stuttgart'),
    ('SXF', 'Berlín-Schönefeld'), ('TXL', 'Berlín-Tegel'), ('BUD', 'Budapešť'),
    ('KRK', 'Krakov'), ('WAW', 'Varšava');

CREATE TABLE airport_prefs (
    airport INT NOT NULL REFERENCES airports(id),
    high_pref BOOLEAN NOT NULL
);


CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    price FLOAT8 NOT NULL,
    hotel INT NOT NULL REFERENCES hotels(id),
    food_package INT NOT NULL REFERENCES food_packages(id),
    airport INT NOT NULL REFERENCES airports(id),
    persons INT NOT NULL,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL
);

INSERT INTO hotels (name, stars, beach_dist)
VALUES
    ('Hotel Blue Fish', 4, 1),
    ('Antal', 4, 2),
    ('Golden Star', 5, 3),
    ('Divan Cave Hotel', 4, 4);

CREATE TABLE reviews (
    hotel INT NOT NULL REFERENCES hotels(id),
    rating FLOAT NOT NULL
);

INSERT INTO reviews (hotel, rating)
VALUES
(1, 9), (1, 10), (1, 8),
(2, 4), (2, 8), (2, 8),
(3, 7), (3, 6),
(4, 2), (4, 6), (4, 7);

INSERT INTO trips (price, hotel, food_package, airport, persons, date_from, date_to)
VALUES
    (18000.0, 1, 3, 7, 5, '2025-6-6', '2025-6-16'),
    (20000.0, 2, 4, 1, 4, '2025-6-17', '2025-6-27'),
    (20000.0, 2, 4, 1, 4, '2025-6-17', '2025-6-27'),
    (35000.0, 4, 2, 2, 4, '2025-7-9', '2025-7-19'),
    (26000.0, 2, 3, 7, 3, '2025-7-20', '2025-7-30'),
    (29000.0, 3, 2, 3, 5, '2025-7-31', '2025-8-10');


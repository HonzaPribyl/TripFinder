CREATE TABLE locations (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR NOT NULL
);

INSERT INTO locations (name) VALUES
    ('Turecko'), ('Řecko'), ('Egypt'), ('Španělsko'),
    ('Tunisko'), ('Chorvatsko'), ('Itálie'), ('Bulharsko'),
    ('Kypr'), ('Spojené Arabské Empiráty'), ('Kapverdské ostrovy'),
    ('Dominikánská republika'), ('Maledivy'), ('Zanzibar a Tanzánie'),
    ('Kuba'), ('Kypr'), ('Thajsko');

CREATE TABLE location_prefs (
                                location INT NOT NULL REFERENCES locations(id),
                                high_pref BOOLEAN NOT NULL
);

CREATE TABLE beach_distance (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO beach_distance (name) VALUES ('Přímo u pláže'), ('Do 5 min'), ('Do 15 min'), ('Více, než 15 min');

CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    stars INT NOT NULL,
    location INT NOT NULL REFERENCES locations(id),
    beach_dist INT NOT NULL REFERENCES beach_distance(id),
    family_friendly BOOLEAN NOT NULL,
    wifi BOOLEAN NOT NULL,
    swimming_pool BOOLEAN NOT NULL
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

INSERT INTO hotels (name, stars, location, beach_dist, family_friendly, wifi, swimming_pool)
VALUES
    ('Anahtar', 3, 1, 2, true, true, true),
    ('Villa Sonata Apart Hotel', 3, 1, 3, true, true, true),
    ('Kleopatra Bebek Hotel', 3, 1, 2, true, true, true),
    ('The Garden Beach Hotel', 4, 1, 1, true, true, true),
    ('Club Calimera Kaya Side', 5, 1, 1, true, true, true),
    ('Simply Fine Hotel Alize', 3, 1, 2, true, true, true),
    ('Rena Studios', 2, 2, 3, true, true, true),
    ('Medusa Hotel', 3, 2, 4, true, true, true),
    ('Kos Bay', 2, 2, 2, false, true, false),
    ('Rotonda Hotel', 3, 2, 3, false, true, false),
    ('Ilios', 3, 2, 3, false, true, true),
    ('Triton Garden', 3, 2, 4, false, false, true),
    ('Georgia Apartments Afandou', 3, 2, 4, true, true, true),
    ('Katrin Hotel & Bungalows', 4, 2, 3, true, true, true),
    ('Aquila Rithymna Beach', 5, 2, 1, true, true, true),
    ('Sharm Resort', 4, 3, 1, false, true, true),
    ('Empire Beach Aqua Park', 3, 3, 1, true, true, true),
    ('Sharm Plaza', 4, 3, 1, false, true, true),
    ('Ghazala Gardens', 3, 3, 2, true, true, true),
    ('King Tut Aqua Park Beach Resort', 4, 3, 1, true, true, true),
    ('Hotel Pickalbatros Palace Port Ghalib', 5, 3, 1, false, false, false),
    ('Albatros White Beach', 5, 3, 1, true, true, true),
    ('Hostal Alcina', 1, 4, 4, true, true, true),
    ('Hotel San Francisco', 3, 4, 2, false, true, true),
    ('Vista Sol', 2, 4, 3, false, true, true),
    ('Playamar Hotel & Apartmentos', 2, 4, 2, true, true, true),
    ('Best Delta', 4, 4, 4, true, true, true),
    ('Hotel Caribbean Bay', 3, 4, 2, true, true, true),
    ('Palma Bay Club Resort', 3, 4, 3, true, true, true),
    ('Golf Residence Hotel', 4, 5, 3, false, true, true),
    ('Marabout', 3, 5, 3, true, true, true),
    ('Nesrine', 4, 5, 2, true, true, true),
    ('Hannibal Palace', 3, 5, 2, true, true, true),
    ('Hotel Liberty Resort', 4, 5, 2, true, true, true),
    ('Hotel Tropicana Club & SPA', 3, 5, 1, true, true, true),
    ('Hotel Medina Solaria & Thalasso', 5, 5, 1, false, false, true),
    ('Zodiac Hammamet', 4, 5, 2, false, true, true),
    ('Lanterna Resort', 2, 6, 1, false, true, false),
    ('Ad Turres', 3, 6, 2, false, true, true),
    ('Apartments Polynesia Plava Laguna', 3, 6, 3, true, true, true),
    ('Horizont Resort', 2, 6, 2, true, true, true),
    ('Apartmány Roic', 3, 6, 2, false, true, false),
    ('Apartments Ivana', 4, 6, 2, false, true, false),
    ('Apartments Kanegra Plava Laguna', 2, 6, 2, true, true, false),
    ('Hotel Kristal', 4, 6, 1, true, true, true),
    ('Hotel Pinija', 4, 6, 1, true, false, false),
    ('Remisens Hotel Albatros', 4, 6, 1, true, true, true),
    ('Hotel L Ancora', 3, 7, 2, false, true, true),
    ('Park Imperial Terme', 3, 7, 3, false, true, true),
    ('Aragonese', 3, 7, 2, false, true, true),
    ('Osimar Hotel', 3, 7, 3, false, true, false),
    ('Baia del Capo', 3, 7, 3, true, true, true),
    ('Villa Teresa', 3, 7, 2, false, true, true),
    ('Old River', 3, 7, 2, false, true, true),
    ('Galidon', 3, 7, 3, true, true, true),
    ('Vezhen', 3, 8, 2, true, true, true),
    ('Larisa', 3, 8, 2, false, true, true),
    ('Panorama', 3, 8, 3, false, true, false),
    ('Katja', 1, 8, 3, false, false, false),
    ('Ivana Palace', 4, 8, 2, true, true, true),
    ('Continental Park Hotel', 3, 8, 2, true, true, true),
    ('Marion Hotel', 2, 9, 3, true, true, true),
    ('Panareti Coral Bay Resort', 3, 9, 2, true, true, true),
    ('Sunny Hill Hotel Apartments', 3, 9, 3, false, true, true),
    ('Sweet Memories', 2, 9, 3, false, true, true),
    ('Theo Sunset Bay Holiday Village', 4, 9, 3, false, true, false),
    ('Cynthiana Beach Hotel', 3, 9, 1, true, true, true),
    ('Amarande', 5, 9, 2, false, true, false),
    ('Pernera Beach Hotel', 4, 9, 1, true, true, true),
    ('Citymax Bur Dubai', 3, 10, 3, false, true, true),
    ('Saffron Boutique Hotel', 4, 10, 3, true, true, true),
    ('Arabian Park Dubai Edge', 3, 10, 3, false, true, true),
    ('Signature 1 Hotel Tecom', 4, 10, 3, false, true, true),
    ('Canopy by Hilton Dubai Al Seef', 4, 10, 3, false, true, true),
    ('Fairmont Ajman', 5, 10, 2, true, true, true),
    ('Rixos Premium Dubai JBR', 5, 10, 2, true, true, true),
    ('Royal M Hotel & Resort by Gewan – Al Aqah', 5, 10, 1, true, true, true),
    ('Hotel Porto Antigo', 3, 11, 3, false, true, true),
    ('Meliá Dunas Beach Resort & Spa', 5, 11, 2, true, false, true),
    ('Murdeira Village Resort', 4, 11, 2, true, true, true),
    ('Ouril Hotel Agueda', 3, 11, 3, false, true, true),
    ('Pontao', 3, 11, 3, false, true, true),
    ('VIK Hotel Arena Blanca', 4, 12, 3, true, true, true),
    ('Vista Sol Punta Cana Beach Resort & Spa', 4, 12, 3, true, true, true),
    ('Playabachata resort', 5, 12, 3, true, true, true),
    ('Viva Wyndham Tangerine', 4, 12, 3, true, true, true),
    ('Embudu Village Maldives', 3, 13, 3, false, true, false),
    ('Thulhagiri Island Resort', 4, 13, 3, false, true, true),
    ('Eriyadu Island Resort', 3, 13, 3, false, true, true),
    ('Kuredu Resort & Spa', 4, 13, 3, true, true, true),
    ('Paradise Beach Resort', 4, 14, 3, false, true, true),
    ('Reef & Beach Resort', 3, 14, 3, false, true, true),
    ('Tanzanite Beach Resort', 3, 14, 3, true, true, true),
    ('Paradise Beach Resort', 4, 14, 3, false, true, true),
    ('Iberostar Bella Costa', 4, 15, 3, true, true, true),
    ('Grand Memories Varadero', 5, 15, 3, true, true, true),
    ('Chom View Hotel', 3, 16, 1, true, true, true),
    ('Hotel Tropicana', 3, 16, 2, true, true, true),
    ('Pinnacle Grand Jomtien Resort & Beach Club', 3, 16, 1, true, true, true),
    ('Ocean Breeze Resort Khaolak', 3, 16, 1, true, true, true);

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


CREATE DATABASE IF NOT EXISTS insurance CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE insurance;

-- POISTENEC
CREATE TABLE poistenec (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           meno VARCHAR(100) NOT NULL,
                           priezvisko VARCHAR(100) NOT NULL,
                           rodne_cislo VARCHAR(20) NOT NULL UNIQUE,
                           email VARCHAR(255) NOT NULL UNIQUE,

    -- Trvalá adresa
                           trvale_psc VARCHAR(10) NOT NULL,
                           trvale_obec VARCHAR(100) NOT NULL,
                           trvale_ulica VARCHAR(100) NOT NULL,
                           trvale_cislo_domu VARCHAR(10) NOT NULL,

    -- Korešpondenčná adresa (môže byť NULL - fallback na trvalú)
                           korespondencna_psc VARCHAR(10),
                           korespondencna_obec VARCHAR(100),
                           korespondencna_ulica VARCHAR(100),
                           korespondencna_cislo_domu VARCHAR(10)
);

-- ZMLUVA (abstraktná base class)
CREATE TABLE zmluva (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        cislo_zmluvy VARCHAR(50) NOT NULL UNIQUE,
                        datum_vzniku DATE NOT NULL,
                        typ_zmluvy VARCHAR(50) NOT NULL,

                        poistenec_id BIGINT NOT NULL,
                        FOREIGN KEY (poistenec_id) REFERENCES poistenec(id) ON DELETE CASCADE
);

-- POISTENIE MAJETKU
CREATE TABLE poistenie_majetku (
                                   id BIGINT PRIMARY KEY, -- rovnaké ako v zmluva
                                   typ_nehnutelnosti ENUM('Byt', 'Rodinný dom-murovaný', 'Rodinný dom-drevený') NOT NULL,
                                   hodnota_nehnutelnosti DECIMAL(15, 2) NOT NULL,

    -- Adresa nehnuteľnosti
                                   psc VARCHAR(10) NOT NULL,
                                   obec VARCHAR(100) NOT NULL,
                                   ulica VARCHAR(100) NOT NULL,
                                   cislo_domu VARCHAR(10) NOT NULL,

                                   FOREIGN KEY (id) REFERENCES zmluva(id) ON DELETE CASCADE
);

-- CESTOVNE POISTENIE
CREATE TABLE cestovne_poistenie (
                                    id BIGINT PRIMARY KEY, -- rovnaké ako v zmluva
                                    datum_zaciatku DATE NOT NULL,
                                    datum_konca DATE NOT NULL,
                                    zodpovednost_za_skodu BOOLEAN NOT NULL,
                                    uraz BOOLEAN NOT NULL,

                                    FOREIGN KEY (id) REFERENCES zmluva(id) ON DELETE CASCADE
);

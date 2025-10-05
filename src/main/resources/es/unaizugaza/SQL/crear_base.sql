CREATE DATABASE IF NOT EXISTS empresa;
USE empresa;

CREATE TABLE IF NOT EXISTS Persona (
    dni INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL
    );

INSERT INTO Persona (first_name, last_name, birth_date) VALUES
    ('Unai', 'Zugaza', '2006-05-12'),
    ('Ruben', 'Luna', '2005-8-21');
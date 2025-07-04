CREATE DATABASE zenta;
USE zenta;

-- Crear tabla de roles
CREATE TABLE rol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Insertar roles iniciales
INSERT INTO rol (nombre) VALUES ('Admin'), ('Soporte'), ('Usuario');

-- Crear tabla de usuarios (relación con tabla rol)
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    tipo_documento ENUM('DNI', 'CARNET_DE_EXTRANJERIA', 'PASAPORTE') NOT NULL,
    num_documento VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol_id BIGINT NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES rol(id) ON DELETE RESTRICT
);

-- Insertar usuarios de prueba
INSERT INTO user (nombre, apellido, tipo_documento, num_documento, email, contrasena, rol_id) VALUES
('Piero', 'Onocuica', 'DNI', '74971851', 'admin@email.com', 'admin', 1),
('Raquel', 'Callata', 'CARNET_DE_EXTRANJERIA', 'CX983174', 'soporte@email.com', 'soporte', 2),
('Xiomara', 'Perez', 'PASAPORTE', 'PA491820', 'usuario@email.com', 'usuario', 3);

SELECT * FROM user;
-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS estudocrud CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE estudocrud;

-- Tabela Usuário
CREATE TABLE IF NOT EXISTS Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

-- Tabela Matéria
CREATE TABLE IF NOT EXISTS Materia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Tabela Conteúdo
CREATE TABLE IF NOT EXISTS Conteudo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    horas_estudar INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    materia_id INT NOT NULL,
    FOREIGN KEY (materia_id) REFERENCES Materia(id) ON DELETE CASCADE
);

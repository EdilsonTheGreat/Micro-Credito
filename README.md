Para Rodar:
Primeiro- comecem por criar a base de dados micricredito
segundo- criar a tabela cliente
terceiro- ir ao projecto no pacote dao-DBConnection.java e preecher os dados para URL,USER, E PASSWORD

Script
CREATE DATABASE microcredito;
USE microcredito;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bi VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    apelido VARCHAR(100) NOT NULL,
    telefone INT NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    dataCadastro DATE NOT NULL DEFAULT (CURRENT_DATE)
);

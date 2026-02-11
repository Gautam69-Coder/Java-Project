-- Database Schema for Gaming Store
CREATE DATABASE IF NOT EXISTS gaming_store;
USE gaming_store;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255)
);

-- Games Table
CREATE TABLE IF NOT EXISTS games (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255),
    price REAL,
    description VARCHAR(255),
    color_rgb INTEGER
);

-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    game_title VARCHAR(255),
    order_date VARCHAR(255),
    status VARCHAR(255)
);

-- Initial Game Data
INSERT IGNORE INTO games (id, title, price, description, color_rgb) VALUES 
('1', 'Cyberpunk 2077', 59.99, 'Open-world action RPG.', -16711936), -- Yellow-ish
('2', 'Elden Ring', 59.99, 'Fantasy action-RPG.', -65536), -- Gold-ish
('3', 'Minecraft', 26.95, 'Infinite worlds.', -16744448), -- Green
('4', 'RDR 2', 39.99, 'Western epic.', -65536); -- Red

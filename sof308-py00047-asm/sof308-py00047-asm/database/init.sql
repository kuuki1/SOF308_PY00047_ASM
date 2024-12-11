-- Tạo cơ sở dữ liệu nếu chưa có
CREATE DATABASE IF NOT EXISTS news_application;

-- Chọn cơ sở dữ liệu để sử dụng
USE news_application;

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

-- Tạo bảng posts
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    image_url TEXT,
    author_id BIGINT,
    created_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Tạo bảng comments
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT,
    user_id BIGINT,
    content TEXT,
    created_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

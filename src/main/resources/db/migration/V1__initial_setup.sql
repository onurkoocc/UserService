CREATE SCHEMA IF NOT EXISTS users;
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- Insert default roles
INSERT INTO roles (name) VALUES ('RESTAURANT_MANAGER'), ('CUSTOMER'), ('WAITER'), ('KITCHEN');

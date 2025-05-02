CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    user_defined_fields JSON,
    is_fraud BOOLEAN DEFAULT FALSE
);

CREATE TABLE user_likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_user_id BIGINT NOT NULL,
    target_user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (source_user_id) REFERENCES users(id),
    FOREIGN KEY (target_user_id) REFERENCES users(id)
);

CREATE TABLE user_visits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_user_id BIGINT NOT NULL,
    target_user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (source_user_id) REFERENCES users(id),
    FOREIGN KEY (target_user_id) REFERENCES users(id)
);
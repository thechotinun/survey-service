CREATE TABLE responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seq_id UUID,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seq_id) REFERENCES seq(id)
);
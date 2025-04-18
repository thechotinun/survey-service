CREATE TABLE questions (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    seq_id UUID,
    title VARCHAR(255),
    type VARCHAR(50),
    page_number INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seq_id) REFERENCES seq(id)
);
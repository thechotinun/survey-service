CREATE TABLE question_options (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    question_id UUID,
    option_text TEXT,
    option_value VARCHAR(255),
    order_num INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions(id)
);
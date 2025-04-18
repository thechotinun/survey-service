CREATE TABLE question_settings (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    question_id UUID,
    setting_key VARCHAR(100),
    setting_value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions(id)
);
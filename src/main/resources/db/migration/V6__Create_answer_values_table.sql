CREATE TABLE answer_values (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    response_id BIGINT,
    question_id UUID,
    option_id UUID,
    text_value TEXT,
    numeric_value DECIMAL(19,4),
    FOREIGN KEY (response_id) REFERENCES responses(id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (option_id) REFERENCES question_options(id)
);
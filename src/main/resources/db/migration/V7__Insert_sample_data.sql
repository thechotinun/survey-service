-- example seq
INSERT INTO seq (id, title, description, status) VALUES 
('11111111-1111-1111-1111-111111111111', 'แบบสอบถามความพึงพอใจ', 'แบบสอบถามเพื่อประเมินความพึงพอใจในการให้บริการ', 'ACTIVE');

-- example questions
INSERT INTO questions (id, seq_id, title, type, page_number) VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'จากการใช้งาน TTB Touch ท่านพึงพอใจระดับใด', 'rank', 1),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 'หัวข้อไหนของ TTB Touch ที่ท่านคิดว่าควรปรับปรุงมากที่สุด', 'radio', 1),
('cccccccc-cccc-cccc-cccc-cccccccccccc', '11111111-1111-1111-1111-111111111111', 'คำแนะนำอื่นๆ', 'text', 1);

-- example question_options
INSERT INTO question_options (id, question_id, option_text, option_value, order_num) VALUES 
('11111111-aaaa-aaaa-aaaa-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'ความเร็วในการเปิด', '1', 1),
('22222222-aaaa-aaaa-aaaa-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'การค้นหาเมนูที่ใช้บ่อย', '2', 2),
('33333333-aaaa-aaaa-aaaa-333333333333', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'การถอนเงินโดยไม่ใช้บัตร', '3', 3);

-- example question_settings
INSERT INTO question_settings (id, question_id, setting_key, setting_value) VALUES 
('aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'required', 'true'),
('bbbbbbbb-1111-1111-1111-bbbbbbbbbbbb', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'min', '1'),
('cccccccc-1111-1111-1111-cccccccccccc', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'max', '5'),
('cccccccc-2222-2222-2222-cccccccccccc', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'min_title', '1 คือไม่พอใจมาก'),
('dddddddd-1111-1111-1111-dddddddddddd', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'max_title', '5 คือพอใจมาก'),
('dddddddd-2222-2222-2222-dddddddddddd', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'required', 'true'),
('dddddddd-3333-3333-3333-dddddddddddd', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'max_length', '100'),
('eeeeeeee-3333-3333-3333-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'require', 'true');
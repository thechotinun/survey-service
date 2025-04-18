-- example seq
INSERT INTO seq (id, title, description, status) VALUES 
('11111111-1111-1111-1111-111111111111', 'แบบสอบถามความพึงพอใจ', 'แบบสอบถามเพื่อประเมินความพึงพอใจในการให้บริการ', 'ACTIVE'),
('22222222-2222-2222-2222-222222222222', 'แบบสำรวจความคิดเห็น', 'แบบสำรวจความคิดเห็นเกี่ยวกับผลิตภัณฑ์ใหม่', 'DRAFT');

-- example questions
INSERT INTO questions (id, seq_id, title, type, page_number) VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'คุณมีความพึงพอใจในการให้บริการระดับใด?', 'RADIO', 1),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 'ท่านมีข้อเสนอแนะเพิ่มเติมหรือไม่?', 'TEXT', 1),
('cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', 'ท่านชอบฟีเจอร์ใดของผลิตภัณฑ์มากที่สุด?', 'CHECKBOX', 1),
('dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 'กรุณาให้คะแนนความใช้งานง่ายของผลิตภัณฑ์', 'RATING', 2);

-- example question_options
INSERT INTO question_options (id, question_id, option_text, option_value, order_num) VALUES 
('11111111-aaaa-aaaa-aaaa-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'น้อยที่สุด', '1', 1),
('22222222-aaaa-aaaa-aaaa-222222222222', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'น้อย', '2', 2),
('33333333-aaaa-aaaa-aaaa-333333333333', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ปานกลาง', '3', 3),
('44444444-aaaa-aaaa-aaaa-444444444444', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'มาก', '4', 4),
('55555555-aaaa-aaaa-aaaa-555555555555', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'มากที่สุด', '5', 5),
('11111111-cccc-cccc-cccc-111111111111', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'ดีไซน์', 'design', 1),
('22222222-cccc-cccc-cccc-222222222222', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'ฟังก์ชันการใช้งาน', 'function', 2),
('33333333-cccc-cccc-cccc-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'ราคา', 'price', 3),
('44444444-cccc-cccc-cccc-444444444444', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'คุณภาพ', 'quality', 4);

-- example question_settings
INSERT INTO question_settings (id, question_id, setting_key, setting_value) VALUES 
('aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'required', 'true'),
('bbbbbbbb-1111-1111-1111-bbbbbbbbbbbb', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'required', 'false'),
('cccccccc-1111-1111-1111-cccccccccccc', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'required', 'true'),
('cccccccc-2222-2222-2222-cccccccccccc', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'multiple', 'true'),
('dddddddd-1111-1111-1111-dddddddddddd', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'required', 'true'),
('dddddddd-2222-2222-2222-dddddddddddd', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'min', '1'),
('dddddddd-3333-3333-3333-dddddddddddd', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'max', '10');
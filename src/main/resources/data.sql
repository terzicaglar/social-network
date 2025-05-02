INSERT INTO users (first_name, last_name, email, date_of_birth, user_defined_fields)
VALUES ('Alice', 'Smith', 'alice@example.com', DATE '1990-05-10', '{"hobbies": ["cycling","running"], "city": "Berlin"}'),
       ('Bob', 'Johnson', 'bob@example.com', DATE '1985-08-22', '{"pet": "dog", "favorite_color": "blue"}'),
       ('Clara', 'Lee', 'clara@example.com', DATE '1992-11-03', '{"job": "engineer", "language": "German"}'),
       ('David', 'Kim', 'david@example.com', DATE '1988-03-15', '{"music": "jazz", "sport": "tennis"}'),
       ('Eva', 'Brown', 'eva@example.com', DATE '1995-07-30', '{"university": "TU Berlin", "major": "CS"}');

INSERT INTO user_likes (source_user_id, target_user_id)
VALUES (1, 2),
       (1, 3),
       (2, 3),
       (2, 4);

INSERT INTO user_visits (source_user_id, target_user_id)
VALUES (4, 1),
       (1, 4),
       (5, 2);
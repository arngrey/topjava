DELETE FROM meals;
DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES
    ('2025-01-01 10:23:54', 'Breakfast 1', 300, 100000),
    ('2025-01-01 11:23:54', 'Breakfast 2', 300, 100000),
    ('2025-01-01 12:23:54', 'Breakfast 3', 300, 100000);

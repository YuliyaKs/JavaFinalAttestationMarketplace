INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    VALUES (1, 'Невский проспект, 1', 'Санкт-Петербург', '2024-04-05', 'test1@test.com', 'Иван', 'Иванов', '78121234567', 190000, 18280, 2);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    VALUES (2, 'ул. Некрасова, 2', 'Санкт-Петербург', '2024-04-10', 'test1@test.com', 'Мария', 'Иванова', '78121234568', 190000, 11172, 2);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    VALUES (3, 'Красный пр-кт, 1', 'Новосибирск', '2024-04-15', 'test2@test.com', 'Петр', 'Петров', '73831234567', 630000, 29793, 3);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    VALUES (4, 'ул. Каменская, 2', 'Новосибирск', '2024-04-20', 'test2@test.com', 'Сидор', 'Петров', '73831234568', 630000, 1602, 3);
INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    VALUES (5, 'ул. Новогодняя, 3', 'Новосибирск', '2024-04-25', 'test2@test.com', 'Елена', 'Петрова', '73831234569', 630000, 1540, 3);

INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (1, 92);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (1, 85);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (2, 18);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (2, 15);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (3, 107);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (3, 86);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (3, 87);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (4, 34);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (4, 38);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (5, 43);
INSERT INTO orders_perfumes (order_id, perfumes_id) VALUES (5, 32);

INSERT INTO orders (id, address, city, date, email, first_name, last_name, phone_number, post_index, total_price, user_id)
    VALUES (111, 'Невский пр-кт, 1', 'Санкт-Петербург', '2024-04-05', 'test123@test.com', 'Иван', 'Иванов', '78121234567', 190000, 15821, 122);

insert into orders_perfumes (order_id, perfumes_id) values (111, 2);
insert into orders_perfumes (order_id, perfumes_id) values (111, 4);

-- пароль: admin123
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code)
    values(1, 'admin@gmail.com', 'Адам', 'Адамин', null, null, null, null, null, true, '$2a$08$kS2f5m8eYoNpc.ZECzndGuXiqaWmCaFfOGMQquodP48qrD7.cQG4y', null);

-- пароль: admin123
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code)
    values(122, 'test123@test.com', 'Иван', 'Иванов', 'Санкт-Петербург', 'Невский пр-кт, 1', '78121234567', '190000', null, true, '$2a$08$kS2f5m8eYoNpc.ZECzndGuXiqaWmCaFfOGMQquodP48qrD7.cQG4y', null);

insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code)
    values (126, 'helloworld@test.com', 'Иван2', 'Иванов2', 'Санкт-Петербург', 'Невский пр-кт, 1', '78121234567', '190000','8e97dc37-2cf5-47e2-98e0',false,'$2a$08$kS2f5m8eYoNpc.ZECzndGuXiqaWmCaFfOGMQquodP48qrD7.cQG4y','3f9bcdb0-2241-4c34-803e-598b497d571f');

insert into user_role (user_id, roles) values (1, 'ADMIN');
insert into user_role (user_id, roles) values (122, 'USER');
insert into user_role (user_id, roles) values (126, 'USER');

insert into users_perfume_list (user_id, perfume_list_id) values (122, 44);
insert into users_perfume_list (user_id, perfume_list_id) values (122, 45);

-- users
insert into user (id, first_name, last_name, username, password)
values (1, 'Pera', 'Peric', 'peraperic', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC'); -- 12345

insert into user (id, first_name, last_name, username, password)
values (2, 'Mika', 'Mikic', 'mikamikic', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC'); -- 12345

-- shopping carts
insert into shopping_cart (id, user_id) values (1, 1);

-- roles
insert into role (id, role_name) values (1, 'READER');
insert into role (id, role_name) values (2, 'AUTHOR');
insert into role (id, role_name) values (3, 'EDITOR');

-- user_roles
insert into user_roles (user_id, roles_id) values (1, 1);
insert into user_roles (user_id, roles_id) values (2, 3);

-- sci fields
insert into scientific_field (id, name) values(1, 'MEDICINE');
insert into scientific_field (id, name) values(2, 'ECOLOGY');
insert into scientific_field (id, name) values(3, 'BIOINFORMATICS');
insert into scientific_field (id, name) values(4, 'QUANTUM_INFORMATION_TECHNOLOGY');

-- magazines
insert into magazine (id, title, issn, scientific_field_id) values (1, 'Inside Ecology', '2049-3630', 1);
insert into magazine (id, title, issn, scientific_field_id) values (2, 'Future Medicine', '2049-3631', 2);
insert into magazine (id, title, issn, scientific_field_id) values (3, 'Current Bioinformatics', '2049-3632', 3);

-- editorial boards
insert into editorial_board(id, main_editor_id, magazine_id) values (1, 2, 1);

-- memberships (TODO add: 1 mouth, 6 months, 1 year)
insert into membership (id, subscription_type, magazine_id) values (1, 'READER_PAYS', 1);
insert into membership (id, subscription_type, magazine_id) values (2, 'READER_PAYS', 2);
insert into membership (id, subscription_type, magazine_id) values (3, 'OPEN_ACCESS', 3);

-- insert into membership_magazines(magazines_id, membership_id) values (1, 1);

-- pricelist item
insert into pricelist_item (id, price, subscription_type, magazine_id) values (1, 339.00, 'READER_PAYS', 1);
insert into pricelist_item (id, price, subscription_type, magazine_id) values (2, 219.50, 'READER_PAYS', 2);

-- pricelist
insert into pricelist (id, start_date, end_date) values (1, '2019-01-01', '2020-01-01');
insert into pricelist (id, start_date, end_date) values (2, '2020-01-01', '2021-01-01');

-- pricelist_pricelist_items
insert into pricelist_pricelist_items (pricelist_id, pricelist_items_id) values (1, 1);
insert into pricelist_pricelist_items (pricelist_id, pricelist_items_id) values (1, 2);
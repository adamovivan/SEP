-- users
insert into user (id, first_name, last_name, username, email, password)
values (1, 'Pera', 'Peric', 'peraperic', 'peraperic@gmail.com', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC'); -- 12345

insert into user (id, first_name, last_name, username, email, password)
values (2, 'Mika', 'Mikic', 'mikamikic', 'mikamikic@gmail.com', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC'); -- 12345

insert into user (id, first_name, last_name, username, email, password)
values (3, 'Ana', 'Anic', 'anaanic', 'anaanic@gmail.com', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC'); -- 12345

insert into user (id, first_name, last_name, username, email, password)
values (4, 'Jelena', 'Jelenic', 'jelenajelenic', 'jelenajelenic@gmail.com', '$2a$10$kx6ymttdiBQ/3NAz1ssxoeOF8Vwm3LSKFnEkSADPc5x8kgaj/vKnC'); -- 12345

-- shopping carts
insert into shopping_cart (id, user_id) values (1, 1);

-- roles
insert into role (id, role_name) values (1, 'READER');
insert into role (id, role_name) values (2, 'AUTHOR');
insert into role (id, role_name) values (3, 'EDITOR');

-- user_roles
insert into user_roles (user_id, roles_id) values (1, 1);
insert into user_roles (user_id, roles_id) values (2, 3);
insert into user_roles (user_id, roles_id) values (3, 2);
insert into user_roles (user_id, roles_id) values (4, 2);

-- sci fields
insert into scientific_field (id, name) values(1, 'MEDICINE');
insert into scientific_field (id, name) values(2, 'ECOLOGY');
insert into scientific_field (id, name) values(3, 'BIOINFORMATICS');
insert into scientific_field (id, name) values(4, 'QUANTUM_INFORMATION_TECHNOLOGY');
insert into scientific_field (id, name) values(5, 'MATH');
insert into scientific_field (id, name) values(6, 'BIOLOGY');
insert into scientific_field (id, name) values(7, 'ASTRONOMY');
insert into scientific_field (id, name) values(8, 'PSYCHOLOGY');
insert into scientific_field (id, name) values(9, 'PHILOSOPHY');

-- magazines
insert into magazine (id, title, issn) values (1, 'Aenean viverra cursus', '2049-3630');
insert into magazine (id, title, issn) values (2, 'Sociis natoque penatibus et', '2049-3631');
insert into magazine (id, title, issn) values (3, 'Sit amet luctus', '2049-3632');

-- magazine sci fields
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (1, 1);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (1, 2);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (1, 3);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (1, 7);

insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (2, 2);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (2, 9);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (2, 8);

insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (3, 3);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (3, 4);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (3, 5);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (3, 6);
insert into magazine_scientific_fields (magazine_id, scientific_fields_id) values (3, 1);

-- articles
insert into article (id, magazine_id, title, abstract_text, pdf_path, main_author_id)
values (1, 1, 'Tincidunt tortor aliquam nulla facilisi cras fermentum odio', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vitae aliquet nec ullamcorper sit amet risus nullam. Convallis tellus id interdum velit laoreet id donec ultrices. Lacus suspendisse faucibus interdum posuere. Ut eu sem integer vitae justo eget magna. Ornare arcu odio ut sem. Tellus rutrum tellus pellentesque eu tincidunt. Aliquet bibendum enim facilisis gravida neque convallis a cras semper. Eget nulla facilisi etiam dignissim. Varius vel pharetra vel turpis nunc eget lorem dolor. Viverra orci sagittis eu volutpat odio facilisis mauris. Sagittis orci a scelerisque purus semper eget duis. Rutrum tellus pellentesque eu tincidunt. In dictum non consectetur a. Sagittis nisl rhoncus mattis rhoncus urna neque viverra justo. Enim eu turpis egestas pretium. Pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Duis ut diam quam nulla porttitor. Tortor at risus viverra adipiscing. Morbi tincidunt augue interdum velit euismod in.', null, 3);

insert into article (id, magazine_id, title, abstract_text, pdf_path, main_author_id)
values (2, 1, 'Mi proin sed libero enim sed faucibus turpis in', 'Ornare arcu odio ut sem nulla. Tellus at urna condimentum mattis pellentesque id. Facilisis leo vel fringilla est ullamcorper eget nulla facilisi. Nec ullamcorper sit amet risus. Mi proin sed libero enim sed faucibus turpis in. Ut sem nulla pharetra diam sit. Nisl vel pretium lectus quam. Quisque id diam vel quam elementum pulvinar etiam. Dui ut ornare lectus sit amet est placerat. Ultrices dui sapien eget mi. Massa enim nec dui nunc mattis enim ut tellus. Eu sem integer vitae justo eget magna fermentum iaculis.', null, 3);

insert into article (id, magazine_id, title, abstract_text, pdf_path, main_author_id)
values (3, 1, 'Commodo viverra maecenas accumsan lacus vel facilisis volutpat est velit', 'Sit amet dictum sit amet justo donec enim diam vulputate. Elementum nisi quis eleifend quam adipiscing. Quam pellentesque nec nam aliquam sem et tortor consequat id. Malesuada proin libero nunc consequat. Egestas sed tempus urna et. Tortor at risus viverra adipiscing. Commodo viverra maecenas accumsan lacus vel facilisis volutpat est velit. Donec adipiscing tristique risus nec feugiat in fermentum posuere. Nisl rhoncus mattis rhoncus urna neque viverra. Ac tortor dignissim convallis aenean et tortor. Ultricies mi quis hendrerit dolor magna eget est.', null, 4);

insert into article (id, magazine_id, title, abstract_text, pdf_path, main_author_id)
values (4, 2, 'Pellentesque dignissim enim sit amet venenatis urna cursus', 'Volutpat commodo sed egestas egestas fringilla. Leo integer malesuada nunc vel. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc. Tincidunt vitae semper quis lectus nulla at. Felis bibendum ut tristique et egestas quis ipsum. Ac tortor dignissim convallis aenean. Sed euismod nisi porta lorem mollis. Elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Blandit volutpat maecenas volutpat blandit. Mauris pellentesque pulvinar pellentesque habitant morbi. Pellentesque dignissim enim sit amet venenatis urna cursus. Nulla pellentesque dignissim enim sit. Nunc eget lorem dolor sed viverra. Viverra orci sagittis eu volutpat odio facilisis mauris sit. Et sollicitudin ac orci phasellus egestas tellus. Pellentesque massa placerat duis ultricies lacus sed turpis tincidunt id. Tempor commodo ullamcorper a lacus vestibulum sed arcu non odio.', null, 3);

insert into article (id, magazine_id, title, abstract_text, pdf_path, main_author_id)
values (5, 3, 'Nibh praesent tristique magna sit amet purus', 'Sagittis orci a scelerisque purus semper eget duis at tellus. Sagittis id consectetur purus ut faucibus. Erat velit scelerisque in dictum non consectetur. Quam nulla porttitor massa id neque aliquam. Nibh praesent tristique magna sit amet purus. Nam aliquam sem et tortor. Elit pellentesque habitant morbi tristique senectus et netus et. Porttitor massa id neque aliquam vestibulum. Placerat vestibulum lectus mauris ultrices eros in. In hac habitasse platea dictumst vestibulum rhoncus est pellentesque elit. Nec feugiat nisl pretium fusce id. Blandit aliquam etiam erat velit scelerisque in dictum non consectetur.', null, 4);

-- articles scientific fields
insert into article_scientific_fields (article_id, scientific_fields_id) values (1, 2);
insert into article_scientific_fields (article_id, scientific_fields_id) values (1, 7);
insert into article_scientific_fields (article_id, scientific_fields_id) values (2, 1);
insert into article_scientific_fields (article_id, scientific_fields_id) values (2, 2);
insert into article_scientific_fields (article_id, scientific_fields_id) values (3, 3);
insert into article_scientific_fields (article_id, scientific_fields_id) values (4, 9);
insert into article_scientific_fields (article_id, scientific_fields_id) values (4, 8);
insert into article_scientific_fields (article_id, scientific_fields_id) values (5, 6);

-- magazine articles
-- insert into magazine_articles(magazine_id, articles_id) values (1, 1);
-- insert into magazine_articles(magazine_id, articles_id) values (1, 2);
-- insert into magazine_articles(magazine_id, articles_id) values (1, 3);
-- insert into magazine_articles(magazine_id, articles_id) values (2, 4);
-- insert into magazine_articles(magazine_id, articles_id) values (3, 5);

-- editorial boards
insert into editorial_board(id, main_editor_id, magazine_id) values (1, 2, 1);

-- memberships
insert into membership (id, subscription_type, magazine_id, user_id) values (1, 'READER_PAYS', 1, 2);
insert into membership (id, subscription_type, magazine_id, user_id) values (2, 'READER_PAYS', 2, 2);
insert into membership (id, subscription_type, magazine_id, user_id) values (3, 'READER_PAYS', 3, 2);
insert into membership (id, subscription_type, article_id, user_id) values (4, 'READER_PAYS', 1, 2);
insert into membership (id, subscription_type, article_id, user_id) values (5, 'OPEN_ACCESS', 2, 2);
insert into membership (id, subscription_type, article_id, user_id) values (6, 'READER_PAYS', 3, 2);
insert into membership (id, subscription_type, article_id, user_id) values (7, 'READER_PAYS', 4, 2);
insert into membership (id, subscription_type, article_id, user_id) values (8, 'READER_PAYS', 5, 2);
-- insert into membership (id, subscription_type, magazine_id) values (3, 'OPEN_ACCESS', 3);

-- article membership
update article set membership_id=4 where id=1;
update article set membership_id=5 where id=2;
update article set membership_id=6 where id=3;
update article set membership_id=7 where id=4;
update article set membership_id=8 where id=5;

-- pricelist item
insert into pricelist_item (id, price, membership_id) values (1, 339.00, 1);
insert into pricelist_item (id, price, membership_id) values (2, 219.50, 2);
insert into pricelist_item (id, price, membership_id) values (3, 33219.50, 3);
insert into pricelist_item (id, price, membership_id) values (4, 31.50, 4);
insert into pricelist_item (id, price, membership_id) values (5, 49.80, 5);
insert into pricelist_item (id, price, membership_id) values (6, 22.40, 6);
insert into pricelist_item (id, price, membership_id) values (7, 29.00, 7);
insert into pricelist_item (id, price, membership_id) values (8, 25.60, 8);

-- pricelist
insert into pricelist (id, start_date, end_date) values (1, '2019-01-01', '2020-01-01');
insert into pricelist (id, start_date, end_date) values (2, '2020-01-01', '2021-01-01');

-- pricelist_pricelist_items
insert into pricelist_pricelist_items (pricelist_id, pricelist_items_id) values (1, 1);
insert into pricelist_pricelist_items (pricelist_id, pricelist_items_id) values (1, 2);
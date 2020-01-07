-- clients
insert into client (id, first_name, last_name, date_of_birth, address, city, id_card_number)
values (1, 'Ana', 'Ananic', '1995-03-22', 'Nikole Tesle 100', 'Novi Sad', '2203958540301');

-- cards
insert into card (id, card_brand, pan, cardholder_name, cvv, expiry_date, blocked)
values (1, 'VISA', '4543210000000233', 'Ana Ananic', '810', '2021-05-01', false);

-- bank accounts
insert into bank_account(id, client_id, card_id, balance)
values (1, 1, 1, 10000);

-- update foreign keys
update client set bank_account_id=1 where id = 1;
update card set bank_account_id=1 where id=1;
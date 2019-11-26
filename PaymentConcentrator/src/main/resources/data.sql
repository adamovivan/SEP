-- clients
insert into client (id, first_name, last_name, client_username, magazine_id) values (1, 'Mika', 'Mikic', 'mikamikic', 1);

-- payments
insert into payment (id, type, payment_id, payment_secret, client_id, success_url, cancel_url) values (1, 'PAYPAL',
'AWure9h6GXJwJ3YEfVd5YPIpnqrldESUsElWx9B5Kr8Ykc4_cvz9QM5KaTv4Jh743W3B_PMEBtjFfuJd',
'EBOgOZbYvvThwaeKGiPgTTjYs4NIxGuNZEcE6SPnshx_K-GgspvKhgQzBfbguYJ2ioq1kGMVfu6k7bc0', 1,
'http://localhost:4200/success', 'http://localhost:4200/cancel');
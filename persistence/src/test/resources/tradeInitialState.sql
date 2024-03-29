delete from auth;
delete from user_role;
delete from users;
delete from cryptocurrency;
delete from offer;

INSERT INTO user_role (id,description) VALUES (0,'supervisor');
INSERT INTO user_role (id,description) VALUES (1,'seller');

INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (0,'gbeade@itba.edu.ar', 20, 4, '12345678', '2022-05-01 02:08:03');
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (1,'scastagnino@itba.edu.ar', 14, 7, '87654321', '2022-05-01 02:08:03');

INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 1234, 1, 0, 'gbeade', 'pass_gbeade');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 4321, 1, 1, 'scastagnino', 'pass_scastagnino');

INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 22.1, 'Bitcoin');
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 54.3, 'Ether');

INSERT INTO offer (offer_id,seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (0,0, '2020-11-01 01:18:33', 'BTC', 'APR', 10.33, 4.2, 2.6, 'Im selling BTC');
INSERT INTO offer (offer_id,seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (1,0, '2003-04-27 21:03:02', 'ETH', 'APR', 6, 10.7, 1, 'Im selling ETH');
INSERT INTO offer (offer_id,seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (2,1, '2001-06-27 22:05:02', 'ETH', 'APR', 6.5, 16.7, 0.4, 'Im selling ETH');

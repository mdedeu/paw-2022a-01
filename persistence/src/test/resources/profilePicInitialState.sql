INSERT INTO user_role (description) VALUES ('supervisor');
INSERT INTO user_role (description) VALUES ('seller');

INSERT INTO users (email, rating_sum, rating_count, phone_number, last_login) VALUES ('gbeade@itba.edu.ar', 20, 4, '12345678', '2022-05-01 02:08:03');
INSERT INTO users (email, rating_sum, rating_count, phone_number, last_login) VALUES ('scastagnino@itba.edu.ar', 14, 7, '87654321', '2022-05-01 02:08:03');

INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 1234, 1, 0, 'gbeade', 'pass_gbeade');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 4321, 1, 1, 'scastagnino', 'pass_scastagnino');
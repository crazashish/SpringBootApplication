INSERT INTO Greeting (name) values ('HELLO WORLD');
INSERT INTO Greeting (name) values ('HELLO SPRING');

-- password is 'password'
INSERT INTO Account (username, password, enabled, credentialexpired, expired, locked) VALUES ('user', '$2a$10$9/44Rne7kQqPXa0cY6NfG.3XzScMrCxFYjapoLq/wFmHz7EC9praK', true, true, true, true);
-- password is 'operations'
INSERT INTO Account (username, password, enabled, credentialexpired, expired, locked) VALUES ('operations', '$2a$10$CoMVfutnv1qZ.fNlHY1Na.rteiJhsDF0jB1o.76qXcfdWN6As27Zm', true, false, false, false);

INSERT INTO Role (id, code, label) VALUES (1, 'ROLE_USER', 'User');
INSERT INTO Role (id, code, label) VALUES (2, 'ROLE_ADMIN', 'Admin');
INSERT INTO Role (id, code, label) VALUES (3, 'ROLE_SYSADMIN', 'System Admin');

INSERT INTO AccountRole (accountId, roleId) SELECT a.id, r.id FROM Account a, Role r WHERE a.username = 'user' and r.id = 1;
INSERT INTO AccountRole (accountId, roleId) SELECT a.id, r.id FROM Account a, Role r WHERE a.username = 'operations' and r.id = 3;

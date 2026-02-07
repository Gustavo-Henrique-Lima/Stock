INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user (name, password, status, email) VALUES ('Bob', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1, 'bob@gmail.com');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
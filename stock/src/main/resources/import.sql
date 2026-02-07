INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user (name, password, status, email) VALUES ('Bob', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1, 'bob@gmail.com');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO tb_raw_material (rm_code, rm_name, stock_quantity) VALUES ('AC-304-IND', 'Aço 304 Industrial', 150.5);
INSERT INTO tb_raw_material (rm_code, rm_name, stock_quantity) VALUES ('AL-IND', 'Alumínio Industrial', 72.250);
INSERT INTO tb_raw_material (rm_code, rm_name, stock_quantity) VALUES ('LG-CH', 'Lingonte de chumbo', 10);
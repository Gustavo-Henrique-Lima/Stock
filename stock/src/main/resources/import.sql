INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user (name, password, status, email) VALUES ('Bob', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1, 'bob@gmail.com');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO tb_raw_material (rm_code, rm_name, stock_quantity) VALUES ('AC-304-IND', 'Aço 304 Industrial', 150.5);
INSERT INTO tb_raw_material (rm_code, rm_name, stock_quantity) VALUES ('AL-IND', 'Alumínio Industrial', 72.250);
INSERT INTO tb_raw_material (rm_code, rm_name, stock_quantity) VALUES ('LG-CH', 'Lingonte de chumbo', 10);

INSERT INTO tb_product (pr_code, pr_name, price) VALUES ('P001', 'Parafuso Sextavado 8mm', 0.45);
INSERT INTO tb_product (pr_code, pr_name, price) VALUES ('P002', 'Chapa de Aço 2mm', 120.00);
INSERT INTO tb_product (pr_code, pr_name, price) VALUES ('P003', 'Tubo PVC 100mm', 45.90);

INSERT INTO tb_product_raw_materials (product_id, raw_material_id, required_quantity) VALUES (2, 1, 5.0);
INSERT INTO tb_product_raw_materials (product_id, raw_material_id, required_quantity) VALUES (2, 2, 2.5);
INSERT INTO tb_product_raw_materials (product_id, raw_material_id, required_quantity) VALUES (3, 2, 1.5);
INSERT INTO tb_product_raw_materials (product_id, raw_material_id, required_quantity) VALUES (3, 3, 0.5);
INSERT INTO tb_product_raw_materials (product_id, raw_material_id, required_quantity) VALUES (1, 1, 0.10);

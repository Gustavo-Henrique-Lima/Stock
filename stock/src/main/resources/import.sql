INSERT INTO TB_ROLE (id, authority) VALUES (1, 'ROLE_ADMIN');

INSERT INTO TB_USER (id, name, password, status, email) VALUES (1, 'Bob', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1, 'bob@gmail.com');

INSERT INTO TB_USER_ROLE (user_id, role_id) VALUES (1, 1);

INSERT INTO TB_RAW_MATERIAL (id, rm_code, rm_name, stock_quantity) VALUES (1, 'AC-304-IND', 'Aço 304 Industrial', 150.5);
INSERT INTO TB_RAW_MATERIAL (id, rm_code, rm_name, stock_quantity) VALUES (2, 'AL-IND', 'Alumínio Industrial', 72.250);
INSERT INTO TB_RAW_MATERIAL (id, rm_code, rm_name, stock_quantity) VALUES (3,'LG-CH', 'Lingonte de chumbo', 10);

INSERT INTO TB_PRODUCT (id, pr_code, pr_name, price) VALUES (1, 'P001', 'Parafuso Sextavado 8mm', 0.45);
INSERT INTO TB_PRODUCT (id, pr_code, pr_name, price) VALUES (2, 'P002', 'Chapa de Aço 2mm', 120.00);
INSERT INTO TB_PRODUCT (id, pr_code, pr_name, price) VALUES (3, 'P003', 'Tubo PVC 100mm', 45.90);

INSERT INTO TB_PRODUCT_RAW_MATERIALS (id, product_id, raw_material_id, required_quantity) VALUES (1, 2, 1, 5.0);
INSERT INTO TB_PRODUCT_RAW_MATERIALS (id, product_id, raw_material_id, required_quantity) VALUES (2, 2, 2, 2.5);
INSERT INTO TB_PRODUCT_RAW_MATERIALS (id, product_id, raw_material_id, required_quantity) VALUES (3, 3, 2, 1.5);
INSERT INTO TB_PRODUCT_RAW_MATERIALS (id, product_id, raw_material_id, required_quantity) VALUES (4, 3, 3, 0.5);
INSERT INTO TB_PRODUCT_RAW_MATERIALS (id, product_id, raw_material_id, required_quantity) VALUES (5, 1, 1, 0.10);

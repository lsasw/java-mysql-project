CREATE TABLE product (
                         id BIGINT NOT NULL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         stock INT NOT NULL DEFAULT 0,
                         version INT NOT NULL DEFAULT 0,
                         created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO product (id, name, stock, version)
VALUES (1, 'iPhone 15', 100, 0);
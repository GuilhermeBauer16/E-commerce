CREATE TABLE cart_item (
  id BINARY(16) NOT NULL,
  product_id BINARY(16) DEFAULT NULL,
  quantity INT NOT NULL,
  PRIMARY KEY (id),
  KEY FK_product_id (product_id),
  CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

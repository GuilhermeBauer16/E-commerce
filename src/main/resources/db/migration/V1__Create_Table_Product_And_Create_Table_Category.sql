CREATE TABLE category (
  id binary(16) NOT NULL,
  name varchar(255) DEFAULT NULL UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE product (
  id binary(16) NOT NULL,
  branch varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  is_available bit(1) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  price double DEFAULT NULL,
  quantity int NOT NULL,
  category_id binary(16) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK1mtsbur82frn64de7balymq9s (category_id),
  CONSTRAINT FK1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


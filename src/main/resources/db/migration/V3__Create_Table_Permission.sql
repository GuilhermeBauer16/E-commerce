CREATE TABLE IF NOT EXISTS `permission` (
  id BINARY(16) NOT NULL ,
  description varchar(255) NOT NULL UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
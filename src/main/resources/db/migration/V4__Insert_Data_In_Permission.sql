INSERT INTO `permission` (`id`, `description`) VALUES
  (UNHEX(REPLACE(UUID(), '-', '')), 'ADMIN'),
  (UNHEX(REPLACE(UUID(), '-', '')), 'MANAGER'),
  (UNHEX(REPLACE(UUID(), '-', '')), 'COMMON_USER');
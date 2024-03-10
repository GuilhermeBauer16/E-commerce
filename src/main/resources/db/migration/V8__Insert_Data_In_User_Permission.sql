INSERT INTO `user_permission` (`id_user`, `id_permission`) VALUES
  ((SELECT id FROM users WHERE user_name = 'leandro'), (SELECT id FROM permission WHERE description = 'ADMIN')),
  ((SELECT id FROM users WHERE user_name = 'flavio'), (SELECT id FROM permission WHERE description = 'ADMIN')),
  ((SELECT id FROM users WHERE user_name = 'leandro'), (SELECT id FROM permission WHERE description = 'MANAGER'));
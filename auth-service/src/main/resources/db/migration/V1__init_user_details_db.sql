-- ------------------------------------------------------------
-- Пользователи
-- ------------------------------------------------------------
CREATE TABLE users (
  id                    BIGSERIAL NOT NULL,     -- идентификатор
  username              VARCHAR(64) NOT NULL,   -- имя пользователя (login)
  password              VARCHAR(80) NOT NULL,   -- пароль
  email                 VARCHAR(64),            -- адрес электронной почты, к которому привязан аккаунт
  phone                 VARCHAR(16),            -- телефон, к которому привязан аккаунт
  PRIMARY KEY (id)
);


-- ------------------------------------------------------------
-- Роли пользователей
-- ------------------------------------------------------------
CREATE TABLE roles (
  id                    BIGSERIAL NOT NULL,     -- идентификатор
  name                  VARCHAR(64) NOT NULL,   -- название роли. Пример: ROLE_ADMIN, ROLE_USER, ROLE_MANAGER и так далее
  PRIMARY KEY (id)
);


-- ------------------------------------------------------------
-- Связи пользователей с ролями
-- ------------------------------------------------------------
CREATE TABLE users_roles (
  user_id               BIGINT NOT NULL,        -- идентификатор пользователя
  role_id               BIGINT NOT NULL,        -- идентификатор роли
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);


-- ------------------------------------------------------------
-- Список ролей
-- ------------------------------------------------------------
INSERT INTO roles(name)
VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- ------------------------------------------------------------
-- Пользователь по умолчанию. Username: admin, password: admin
-- ------------------------------------------------------------
INSERT INTO users(username, password)
VALUES ('admin', '$2y$10$tqiwqYVeyWZ69vya0oTUvOTjDyaGNQtRO4w0NvJvw0WZXOSd./FsK');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1);
-- ------------------------------------------------------------
-- Схема базы данных магазина на языке SQL диалекта PostgreSQL
-- ------------------------------------------------------------


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


-- ------------------------------------------------------------
-- Описание товаров
-- ------------------------------------------------------------
CREATE TABLE products (
  id                    BIGSERIAL NOT NULL,     -- идентификатор
  title                 VARCHAR(128) NOT NULL,  -- название
  description           VARCHAR(1024),          -- описание
  price                 NUMERIC(6,2) NOT NULL,  -- стоимость
  PRIMARY KEY (id)
);


-- ------------------------------------------------------------
-- Заказы товаров пользователями
-- ------------------------------------------------------------
CREATE TABLE orders (
  id	                BIGSERIAL NOT NULL,     -- идентификатор
  user_id               BIGINT NOT NULL,        -- идентификатор пользователя, сформировавшего заказ
  phone                 VARCHAR(16) NOT NULL,   -- телефон для связи с пользователем
  delivery_address      VARCHAR(255) NOT NULL,  -- адрес доставки
  delivery_date         DATE NOT NULL,          -- дата доставки заказа
  status                VARCHAR(16) NOT NULL,   -- статус заказа
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);


-- ------------------------------------------------------------
-- Товары, размещённые в заказах
-- ------------------------------------------------------------
CREATE TABLE order_products (
  id	                BIGSERIAL NOT NULL,     -- идентификатор (для Hibernate)
  order_id              BIGINT NOT NULL,        -- идентификатор заказа
  product_id            BIGINT NOT NULL,        -- идентификатор товара
  quantity              INT NOT NULL,           -- количество товара в заказе (штук или грамм)
  price                 NUMERIC(6,2) NOT NULL,  -- зарезервированная стоимость одной единицы товара
  total_price           NUMERIC(9,2) NOT NULL,  -- общая стоимость товара в заказе (с учётом количества и скидок)
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders (id),
  FOREIGN KEY (product_id) REFERENCES products (id)
);


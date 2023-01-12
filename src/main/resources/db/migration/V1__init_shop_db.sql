-- ------------------------------------------------------------
-- Схема базы данных магазина на языке SQL диалекта PostgreSQL
-- ------------------------------------------------------------

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
  username              VARCHAR(36) NOT NULL,   -- идентификатор пользователя, сформировавшего заказ
  phone                 VARCHAR(16) NOT NULL,   -- телефон для связи с пользователем
  delivery_address      VARCHAR(255) NOT NULL,  -- адрес доставки
  delivery_date         DATE NOT NULL,          -- дата доставки заказа
  status                VARCHAR(16) NOT NULL,   -- статус заказа
  PRIMARY KEY (id)
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


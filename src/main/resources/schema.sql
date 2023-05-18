-- 各種テーブル削除
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS favorites;

-- アカウントテーブル
CREATE TABLE accounts
(
   id SERIAL PRIMARY KEY,
   user_id VARCHAR,
   name VARCHAR,
   email VARCHAR,
   introduction TEXT,
   password VARCHAR,
   birthday DATE,
   image_path TEXT,
   created_at TIMESTAMP,
   updated_at TIMESTAMP,
   UNIQUE (user_id, email)
);

CREATE TABLE posts
(
   id SERIAL PRIMARY KEY,
   user_id INTEGER,
   category_id INTEGER,
   body TEXT,
   created_at TIMESTAMP,
   updated_at TIMESTAMP
);

CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   name VARCHAR,
   created_at TIMESTAMP,
   updated_at TIMESTAMP,
   UNIQUE (name)
);

CREATE TABLE comments
(
   id SERIAL PRIMARY KEY,
   post_id INTEGER,
   body TEXT,
   created_at TIMESTAMP,
   updated_at TIMESTAMP
);

CREATE TABLE favorites
(
   id SERIAL PRIMARY KEY,
   user_id INTEGER,
   post_id INTEGER,
   created_at TIMESTAMP,
   updated_at TIMESTAMP
);
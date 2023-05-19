-- 各種テーブル削除
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS favorites;

-- アカウントテーブル
CREATE TABLE accounts
(
   id VARCHAR PRIMARY KEY,
   name VARCHAR,
   email VARCHAR,
   introduction TEXT,
   password VARCHAR,
   birthday DATE,
   image_path TEXT,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   UNIQUE (id, email)
);

CREATE TABLE posts
(
   id SERIAL PRIMARY KEY,
   user_id VARCHAR,
   category_id INTEGER,
   body TEXT,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);

CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   name VARCHAR,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   UNIQUE (name)
);

CREATE TABLE comments
(
   id SERIAL PRIMARY KEY,
   post_id INTEGER,
   body TEXT,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);

CREATE TABLE favorites
(
   id SERIAL PRIMARY KEY,
   user_id VARCHAR,
   post_id INTEGER,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);
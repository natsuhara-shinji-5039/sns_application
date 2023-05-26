-- 各種テーブル削除
DROP VIEW IF EXISTS v_posts;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS reset_passwords;

-- アカウントテーブル
CREATE TABLE accounts
(
   id VARCHAR PRIMARY KEY,
   reset_id VARCHAR,
   name VARCHAR,
   email VARCHAR,
   introduction TEXT,
   password VARCHAR,
   birthday DATE,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   UNIQUE (id, email)
);

-- 投稿テーブル
CREATE TABLE posts
(
   id SERIAL PRIMARY KEY,
   user_id VARCHAR,
   body TEXT,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);

-- 投稿テーブルとアカウントテーブルの結合
CREATE VIEW v_posts AS
(
   SELECT
      p.id,
      a.name,
      p.user_id,
      p.body,
      p.created_at,
      p.updated_at
   FROM posts p
   JOIN accounts a ON p.user_id = a.id
);

-- カテゴリーテーブル
CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   name VARCHAR,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   UNIQUE (name)
);

-- コメントテーブル
CREATE TABLE comments
(
   id SERIAL PRIMARY KEY,
   post_id INTEGER,
   body TEXT,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);

-- お気に入りテーブル
CREATE TABLE favorites
(
   id SERIAL PRIMARY KEY,
   user_id VARCHAR,
   post_id INTEGER,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);

-- パスワードリセットテーブル
CREATE TABLE reset_passwords
(
   id VARCHAR PRIMARY KEY,
   user_id VARCHAR,
   email VARCHAR,
   created_at TIMESTAMP NOT NULL
);
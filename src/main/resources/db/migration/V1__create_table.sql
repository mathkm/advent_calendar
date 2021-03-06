DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS themes;
DROP TABLE IF EXISTS articles;

CREATE TABLE users(
id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT 'ユーザーID',
username VARCHAR(255) NOT NULL COMMENT 'ユーザー名',
password VARCHAR(255) NOT NULL COMMENT 'パスワード',
email VARCHAR(255) NOT NULL COMMENT 'メールアドレス',
role TINYINT NOT NULL COMMENT '権限(0:通常,127:管理者)',
created TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'ユーザー登録日時',
updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ユーザー更新日時')
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE themes(
id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT 'テーマID',
name VARCHAR(255) NOT NULL COMMENT 'テーマ名',
detail TEXT NOT NULL COMMENT 'テーマの詳細',
calendar_month DATE UNIQUE NOT NULL COMMENT 'カレンダーの月(YYYY-MM-DD)',
enabled_dates TEXT NOT NULL COMMENT '登録可能な日',
created_user_id INTEGER NOT NULL COMMENT '登録されたユーザーID',
created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'テーマの登録日時',
updated_user_id INTEGER NOT NULL COMMENT '更新されたユーザーID',
updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'テーマの更新日時')
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE articles(
id INTEGER AUTO_INCREMENT COMMENT '記事のID',
calendar_date DATE NOT NULL COMMENT 'カレンダーの日(YYYY-MM-DD)',
user_id INTEGER NOT NULL COMMENT '記事を登録したユーザーID',
title TEXT NOT NULL COMMENT '記事のタイトル',
url TEXT COMMENT '記事のURL',
created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '記事の登録日時',
updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '記事の更新日時',
PRIMARY KEY (id),
UNIQUE (calendar_date),
INDEX user_id_index (user_id))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users(username,password,email,role)
VALUES('admin','d4a2e4a0d00f43d2082d4afd840a244dee0a1383f9b950cda05ec6630d9e210fd8f062c9026360b9','xxx@illmatics.co.jp','127');
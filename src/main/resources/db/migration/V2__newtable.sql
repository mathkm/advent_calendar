CREATE TABLE themes(
id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT '�e�[�}ID',
name VARCHAR(255) NOT NULL COMMENT '�e�[�}��',
detail TEXT NOT NULL COMMENT '�e�[�}�̏ڍ�',
calendar_month DATE UNIQUE NOT NULL COMMENT '�J�����_�[�̌�(YYYY-MM-DD)',
enable_dates TEXT NOT NULL COMMENT '�o�^�\�ȓ�',
created_user_id INTEGER NOT NULL COMMENT '�o�^���ꂽ���[�U�[ID',
created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�e�[�}�̓o�^����',
updated_user_id INTEGER NOT NULL COMMENT '�X�V���ꂽ���[�U�[ID',
updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�e�[�}�̍X�V����')
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE articles(
id INTEGER AUTO_INCREMENT COMMENT '�L����ID',
calendar_date DATE NOT NULL COMMENT '�J�����_�[�̓�(YYYY-MM-DD)',
user_id INTEGER NOT NULL COMMENT '�L����o�^�������[�U�[ID',
title TEXT NOT NULL COMMENT '�L���̃^�C�g��',
url TEXT COMMENT '�L����URL',
created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '�L���̓o�^����',
updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�L���̍X�V����',
PRIMARY KEY (id),
UNIQUE (calendar_date),
INDEX user_id_index (user_id))
ENGINE=InnoDB DEFAULT CHARSET=utf8;
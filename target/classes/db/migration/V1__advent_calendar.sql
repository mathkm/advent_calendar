CREATE TABLE users(
id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT '���[�U�[ID',
username VARCHAR(255) NOT NULL COMMENT '���[�U�[��',
password VARCHAR(255) NOT NULL COMMENT '�p�X���[�h',
email VARCHAR(255) NOT NULL COMMENT '���[���A�h���X',
role TINYINT NOT NULL COMMENT '����(0:�ʏ�,127:�Ǘ���)',
created TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '���[�U�[�o�^����',
updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '���[�U�[�X�V����');

INSERT INTO users(username,password,email,role)
VALUES('admin','root','xxx@illmatics.co.jp',127);
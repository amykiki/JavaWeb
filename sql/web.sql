SHOW TABLES;
CREATE TABLE t_user (
  ID       SERIAL PRIMARY KEY,
  USERNAME VARCHAR(50) NOT NULL,
  PASSWORD VARCHAR(50) NOT NULL,
  NICKNAME VARCHAR(50),
  ROLE     INT(1), # 0 -- admin user, 1 -- normal user
  STATUS   INT(1) # 0 -- in use, 1 -- offuse
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE t_msg (
  ID        SERIAL PRIMARY KEY,
  TITLE     VARCHAR(100) NOT NULL,
  CONTENT   TEXT,
  CREATTIME DATETIME,
  USERID    BIGINT(20) UNSIGNED,
  CONSTRAINT FOREIGN KEY (USERID) REFERENCES t_user (ID)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

INSERT INTO t_user VALUES (NULL, 'admin', '123', '管理员', 0, 0);
INSERT INTO t_msg VALUES (NULL, 'hello, 大家好', '大家好，这是我的第一篇文章', '2016-03-04 19:37:00', 1);
DESC t_user;
SHOW CREATE TABLE t_user;
SHOW CREATE TABLE t_msg;
COMMIT;
SELECT *
FROM t_user;
SELECT * FROM t_msg;

UPDATE t_user  SET `PASSWORD` = '323232', NICKNAME = '小周周' WHERE id = 14;


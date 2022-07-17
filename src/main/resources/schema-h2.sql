DROP TABLE IF EXISTS users CASCADE;

-- 사용자 데이터이다.
CREATE TABLE users
(
    seq           bigint      NOT NULL AUTO_INCREMENT,              -- 사용자 PK
    username          varchar(10) NOT NULL,                         -- 이름
    email         varchar(50) NOT NULL,                             -- 로그인 이메일
    password        varchar(80) NOT NULL,                           -- 비밀번호
    bio             varchar(2000) default NULL,                     -- 소개글
    PRIMARY KEY (seq),
    CONSTRAINT unq_user_email UNIQUE (email)
);

-- 회원정보 테이블

CREATE TABLE member_tbl (
id varchar(20),
password varchar2(60),
name nvarchar2(100),
gender CHAR,
age number(3,0),
email nvarchar2(50),
mobile nvarchar2(13),
phone nvarchar2(13),
zip CHAR(5),
road_address nvarchar2(50), -- 도로명 주소
jibun_address nvarchar2(50), -- 지번주소
detail_address nvarchar2(50), -- 상세주소
birthday DATE,
joindate DATE DEFAULT current_date,
enabled number(1) DEFAULT 0
);
  
ALTER TABLE member_tbl
ADD CONSTRAINT member_tbl_id_pk PRIMARY KEY(id);

ALTER TABLE member_tbl
ADD CONSTRAINT member_tbl_email_u UNIQUE(email);
 
ALTER TABLE member_tbl
ADD CONSTRAINT member_tbl_mobile_u UNIQUE(mobile);
 
-- 6) 필드명 변경에 따른 제약 조건 일부 변경
ALTER TABLE member_tbl
MODIFY (password CONSTRAINT member_tbl_password_nn NOT NULL);
 
ALTER TABLE member_tbl
MODIFY (name CONSTRAINT member_tbl_name_nn NOT NULL);
 
ALTER TABLE member_tbl
MODIFY (gender CONSTRAINT member_tbl_gender_nn NOT NULL);

ALTER TABLE member_tbl
ADD CONSTRAINT member_tbl_gender_ck CHECK (gender IN ('m', 'f'));
 
ALTER TABLE member_tbl
MODIFY (email CONSTRAINT member_tbl_email_nn NOT NULL);
 
ALTER TABLE member_tbl
MODIFY (mobile CONSTRAINT member_tbl_mobile_nn NOT NULL);
 
ALTER TABLE member_tbl
MODIFY (phone  CONSTRAINT member_tbl_phone_nn NOT NULL);

ALTER TABLE member_tbl
MODIFY (age CONSTRAINT member_tbl_age_nn NOT NULL);

-- 참고 제약조건(constraint) 삭제 예시

-- ALTER TABLE member_tbl DROP CONSTRAINT MEMBER_TBL_EMAIL_U;

-- ALTER TABLE member_tbl DROP CONSTRAINT MEMBER_TBL_MOBILE_U;

-- ALTER TABLE member_tbl DROP CONSTRAINT member_tbl_id_pk; 

-- DROP TABLE member_tbl;

--- spring security 적용에 따른 role(역할) 테이블 추가
--- 외래키 관련 참조 무결 조건 일부 변경

CREATE TABLE user_roles (
  user_role_id number(11) NOT NULL,
  username varchar(20) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  CONSTRAINT fk_username FOREIGN KEY (username) 
     REFERENCES member_tbl (id)
);
 
CREATE SEQUENCE user_roles_seq
	start with 1
	increment by 1
	maxvalue 99999
    nocycle; 

-- remember-me 항목 사용시 정보 저장 (선택 사항)
CREATE TABLE persistent_logins (
   username varchar(64) not null, 
   series varchar(64) primary key, 
   token varchar(64) not null, 
   last_used timestamp not null
);  

-------------------------------------------------------------------------

-- Spring Session JDBC (이 라이브러리를 사용하면 기본적으로 생성해야 됩니다)

CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME NUMBER(19,0) NOT NULL,
	LAST_ACCESS_TIME NUMBER(19,0) NOT NULL,
	MAX_INACTIVE_INTERVAL NUMBER(10,0) NOT NULL,
	EXPIRY_TIME NUMBER(19,0) NOT NULL,
	PRINCIPAL_NAME VARCHAR2(100 CHAR),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR2(200 CHAR) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
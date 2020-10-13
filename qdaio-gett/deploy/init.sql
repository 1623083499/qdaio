-- Create table(接口用户)
CREATE TABLE GETT_USER
(
  ID           NUMBER(20),
  USERID       VARCHAR2(50),
  APPID        VARCHAR2(20),
  NAME         VARCHAR2(200),
  KEY          VARCHAR2(50),
  ACCESS_TOKEN VARCHAR2(50),
  CTIME        DATE,
  EXPDATE      NUMBER(8),
  ENABLED      VARCHAR2(1),
  FORMAL      VARCHAR2(1),
  EXT1         VARCHAR2(200),
  EXT2         VARCHAR2(200),
  EXT3         VARCHAR2(200)
);
-- Add comments to the columns
COMMENT ON TABLE GETT_USER IS '接口用户表';
COMMENT ON COLUMN GETT_USER.ID IS '主键索引';
COMMENT ON COLUMN GETT_USER.USERID IS '用户编号';
COMMENT ON COLUMN GETT_USER.APPID IS '用户政企平台唯一编号';
COMMENT ON COLUMN GETT_USER.NAME IS '用户名称';
COMMENT ON COLUMN GETT_USER.KEY IS '用户接口加解密密钥';
COMMENT ON COLUMN GETT_USER.ACCESS_TOKEN IS '用户接口接入口令';
COMMENT ON COLUMN GETT_USER.CTIME IS '用户创建时间';
COMMENT ON COLUMN GETT_USER.EXPDATE IS '过期时间';
COMMENT ON COLUMN GETT_USER.ENABLED IS '有效标志（1有效，0无效）';
COMMENT ON COLUMN GETT_USER.FORMAL IS '正式标志（1：是,0：否）';
-- Create index
CREATE INDEX "INDEX_GETT_USER1" ON "GETT_USER" ("USERID" ASC) LOGGING VISIBLE;
CREATE INDEX "INDEX_GETT_USER2" ON "GETT_USER" ("APPID" ASC) LOGGING VISIBLE;
CREATE INDEX "INDEX_GETT_USER3" ON "GETT_USER" ("ACCESS_TOKEN" ASC) LOGGING VISIBLE;
-- Create sequence
CREATE SEQUENCE SEQ_GETT_USER MINVALUE 1 MAXVALUE 99999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;


-- Create table(接口日志表)
CREATE TABLE GETT_LOG
(
  ID            NUMBER(20),
  USERID        VARCHAR2(50),
  APPID         VARCHAR2(50),
  BUSIID        VARCHAR2(50),
  KEY           VARCHAR2(50),
  URL           VARCHAR2(200),
  PARAMS        NVARCHAR2(2000),
  CLEARPARAMS   NVARCHAR2(2000),
  HEADERS       NVARCHAR2(2000),
  CDATE         DATE,
  USETIME       NUMBER,
  IP            VARCHAR2(50),
  PREMESSAGE    NVARCHAR2(2000),
  MESSAGE       NVARCHAR2(2000)
);
-- Add comments to the columns 
COMMENT ON TABLE GETT_LOG IS '接口日志表';
COMMENT ON COLUMN GETT_LOG.ID IS '主键ID';
COMMENT ON COLUMN GETT_LOG.USERID IS '用户ID';
COMMENT ON COLUMN GETT_LOG.APPID IS '用户政企平台唯一编号';
COMMENT ON COLUMN GETT_LOG.BUSIID IS '业务ID';
COMMENT ON COLUMN GETT_LOG.KEY IS '密钥';
COMMENT ON COLUMN GETT_LOG.URL IS '请入路径';
COMMENT ON COLUMN GETT_LOG.PARAMS IS '请求参数（解密前）';
COMMENT ON COLUMN GETT_LOG.CLEARPARAMS IS '请求参数（解密后）';
COMMENT ON COLUMN GETT_LOG.HEADERS IS 'http头';
COMMENT ON COLUMN GETT_LOG.CDATE IS '请求时间';
COMMENT ON COLUMN GETT_LOG.USETIME IS '执行时间';
COMMENT ON COLUMN GETT_LOG.IP IS '客户IP';
COMMENT ON COLUMN GETT_LOG.PREMESSAGE IS '返回参数（加密前）';
COMMENT ON COLUMN GETT_LOG.MESSAGE IS '返回参数（加密后）';
-- Create index
CREATE INDEX "INDEX_GETT_LOG1" ON "GETT_LOG" ("USERID" ASC) LOGGING VISIBLE;
CREATE INDEX "INDEX_GETT_LOG2" ON "GETT_LOG" ("APPID" ASC) LOGGING VISIBLE;
CREATE INDEX "INDEX_GETT_LOG3" ON "GETT_LOG" ("BUSIID" ASC) LOGGING VISIBLE;
-- Create sequence
CREATE SEQUENCE SEQ_GETT_LOG MINVALUE 1 MAXVALUE 99999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
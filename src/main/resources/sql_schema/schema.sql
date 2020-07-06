-- psql -h PSQL_HOST -p 5432 -U postgres jrvstrading -f schema.sql
-- Drop table

DROP TABLE IF EXISTS TRADER cascade;
DROP TABLE IF EXISTS ACCOUNT cascade;
DROP TABLE IF EXISTS SECURITY_ORDER cascade;
DROP TABLE IF EXISTS QUOTE cascade;

create table TRADER (
   ID INT NOT NULL AUTO_INCREMENT,
   FIRST_NAME VARCHAR(255) NOT NULL,
   LAST_NAME VARCHAR(255) NOT NULL,
   DOB DATE NOT NULL,
   COUNTRY VARCHAR(255) NOT NULL,
   EMAIL VARCHAR(255) NOT NULL,
   CONSTRAINT trader_pk PRIMARY KEY ( id )
);

CREATE TABLE ACCOUNT
(
  ID        INT NOT NULL AUTO_INCREMENT,
  TRADER_ID INT   NOT NULL,
  AMOUNT    FLOAT(8) NOT NULL,
  CONSTRAINT account_pk PRIMARY KEY (id),
  CONSTRAINT account_trader_fk FOREIGN KEY (trader_id) REFERENCES trader (id)
);

CREATE TABLE QUOTE
(
  TICKER     varchar(255) NOT NULL,
  LAST_PRICE float(8)  NOT NULL,
  BID_PRICE  float(8)  NOT NULL,
  BID_SIZE   int    NOT NULL,
  ASK_PRICE  float(8)  NOT NULL,
  ASK_SIZE   int    NOT NULL,
  CONSTRAINT quote_pk PRIMARY KEY (ticker)
);

CREATE TABLE SECURITY_ORDER
(
  ID         int  NOT NULL,
  ACCOUNT_ID int    NOT NULL,
  STATUS     varchar(255) NOT NULL,
  TICKER     varchar(255) NOT NULL,
  SIZE    int    NOT NULL,
  PRICE      float(8)  NULL,
  NOTES      varchar(255) NULL,
  CONSTRAINT security_order_pk PRIMARY KEY (id),
  CONSTRAINT security_order_account_fk FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT security_order_quote_fk FOREIGN KEY (ticker) REFERENCES quote (ticker)
);

--implement with SQL in repository
DROP VIEW IF EXISTS position;

CREATE OR REPLACE VIEW POSITION
AS
SELECT account_id,
       ticker,
       sum(size) AS position
FROM security_order
WHERE status = 'FILLED'
GROUP BY account_id, ticker;

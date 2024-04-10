create table TRANSAZIONI(
  ID NUMERIC(18) not null AUTO_INCREMENT,
  TRANSACTION_ID NUMERIC(10) not null,
  OPERATION_ID NUMERIC(14) not null,
  ACCOUNTING_DATE DATE,
  VALUE_DATE DATE,
  AMOUNT NUMERIC(20, 3) not null,
  CURRENCY varchar2(20),
  DESCRIPTION varchar2(500) not null,
  PRIMARY KEY ( ID )
);
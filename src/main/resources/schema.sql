CREATE TABLE ACCOUNT (
  ACCOUNT_ID NUMBER NOT NULL PRIMARY KEY,
  NAME VARCHAR (50),
  PHONE_NUMBER NUMBER (10),
  ADDRESS VARCHAR (100)
);

CREATE TABLE INVENTORY (
  PRODUCT_ID NUMBER NOT NULL PRIMARY KEY ,
  AVAILABLE_QUANTITY NUMBER ,
  PRICE DOUBLE,
  PRODUCT_DESCRIPTION VARCHAR (100)
);

CREATE TABLE ORDERS (
  ORDER_ID NUMBER NOT NULL PRIMARY KEY ,
  ACCOUNT_ID NUMBER NOT NULL,
  PRODUCT_ID NUMBER NOT NULL,
  QUANTITY NUMBER NOT NULL ,
  TOTAL_AMOUNT DOUBLE NOT NULL ,
  ORDER_STATUS VARCHAR NOT NULL ,
  DESCRIPTION VARCHAR (100),
  TIMESTAMP_OF_ORDER TIMESTAMP,

  check (ORDER_STATUS in ('PLACED', 'PROCESSING', 'DISPATCHED', 'OUT_FOR_DELIVERY', 'DELIVERED')),
  CONSTRAINT fk_order_account FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT (ACCOUNT_ID),
  CONSTRAINT fk_order_product FOREIGN KEY (PRODUCT_ID) REFERENCES INVENTORY (PRODUCT_ID)
);

INSERT INTO ACCOUNT VALUES (1, 'Amit', 12345, 'ABC');
INSERT INTO ACCOUNT VALUES (2, 'Sumit', 12345, 'ABC');
INSERT INTO ACCOUNT VALUES (3, 'Aman', 12345, 'ABC');
INSERT INTO ACCOUNT VALUES (4, 'Suman', 12345, 'ABC');


INSERT INTO INVENTORY VALUES (1, 10, 10, 'ABC');
INSERT INTO INVENTORY VALUES (2, 5, 20, 'ABC');
INSERT INTO INVENTORY VALUES (3, 3, 30, 'ABC');
INSERT INTO INVENTORY VALUES (4, 6, 40, 'ABC');

INSERT INTO ORDERS (ORDER_ID, ACCOUNT_ID, PRODUCT_ID, QUANTITY, TOTAL_AMOUNT, ORDER_STATUS, DESCRIPTION) VALUES(1, 1, 1, 2, 20, 'PLACED', 'ABC');
INSERT INTO ORDERS (ORDER_ID, ACCOUNT_ID, PRODUCT_ID, QUANTITY, TOTAL_AMOUNT, ORDER_STATUS, DESCRIPTION) VALUES(2, 2, 1, 2, 20, 'PLACED','DEF');
INSERT INTO ORDERS (ORDER_ID, ACCOUNT_ID, PRODUCT_ID, QUANTITY, TOTAL_AMOUNT, ORDER_STATUS, DESCRIPTION) VALUES(3, 3, 2, 2, 20, 'PLACED','GHI');
INSERT INTO ORDERS (ORDER_ID, ACCOUNT_ID, PRODUCT_ID, QUANTITY, TOTAL_AMOUNT, ORDER_STATUS, DESCRIPTION) VALUES(4, 4, 3, 2, 20, 'PLACED','JKL');
INSERT INTO ORDERS (ORDER_ID, ACCOUNT_ID, PRODUCT_ID, QUANTITY, TOTAL_AMOUNT, ORDER_STATUS, DESCRIPTION) VALUES(5, 2, 4, 2, 20, 'PLACED','MNO');
INSERT INTO ORDERS (ORDER_ID, ACCOUNT_ID, PRODUCT_ID, QUANTITY, TOTAL_AMOUNT, ORDER_STATUS, DESCRIPTION) VALUES(6, 1, 3, 2, 20, 'PLACED','PQR');


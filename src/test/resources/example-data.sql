DELETE FROM PRODUCT;
DELETE FROM DISCOUNT;


INSERT INTO PRODUCT(ID, PRICE, NAME, DISCOUNT_TYPE) VALUES('bc631743-d409-4333-bdaf-d92a5c683f4a', 550, 'Bike', 'PERCENTAGE_BASED');
INSERT INTO PRODUCT(ID, PRICE, NAME, DISCOUNT_TYPE) VALUES('a7e26750-f619-4e2a-b843-d71111e41663', 2000, 'TV Sony Ultra Wide', 'PERCENTAGE_BASED');
INSERT INTO PRODUCT(ID, PRICE, NAME, DISCOUNT_TYPE) VALUES('ce3bc457-16f7-420d-b42a-c3b54a191f69', 45.50, 'HDD 100GB', 'AMOUNT_BASED');
INSERT INTO PRODUCT(ID, PRICE, NAME, DISCOUNT_TYPE) VALUES('ccccf981-b82b-431b-86f7-8bb6ba01b542', 3.60, 'Ikea Hot-dog', 'AMOUNT_BASED');

INSERT INTO DISCOUNT (ID, DISCOUNT_VALUE, AMOUNT, DISCOUNT_TYPE) VALUES ((select next value for discount_seq), 2, 10, 'PERCENTAGE_BASED');
INSERT INTO DISCOUNT (ID, DISCOUNT_VALUE, AMOUNT, DISCOUNT_TYPE) VALUES ((select next value for discount_seq), 3, 50, 'PERCENTAGE_BASED');
INSERT INTO DISCOUNT (ID, DISCOUNT_VALUE, AMOUNT, DISCOUNT_TYPE) VALUES ((select next value for discount_seq), 4.5, 100, 'PERCENTAGE_BASED');
INSERT INTO DISCOUNT (ID, DISCOUNT_VALUE, AMOUNT, DISCOUNT_TYPE) VALUES ((select next value for discount_seq), 10, 10, 'AMOUNT_BASED');
INSERT INTO DISCOUNT (ID, DISCOUNT_VALUE, AMOUNT, DISCOUNT_TYPE) VALUES ((select next value for discount_seq), 20, 50, 'AMOUNT_BASED');
INSERT INTO DISCOUNT (ID, DISCOUNT_VALUE, AMOUNT, DISCOUNT_TYPE) VALUES ((select next value for discount_seq), 25, 100, 'AMOUNT_BASED');

COMMIT;
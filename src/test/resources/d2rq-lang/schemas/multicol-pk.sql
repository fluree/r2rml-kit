CREATE TABLE A (ID INT PRIMARY KEY);
CREATE TABLE B (A_ID INT REFERENCES A (ID), B_ID INT, PRIMARY KEY (A_ID, B_ID));
INSERT INTO A VALUES (1);
INSERT INTO B VALUES (1, 100);
INSERT INTO B VALUES (1, 101);

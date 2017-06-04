# --- !Ups
CREATE TABLE Employee (
    ID BIGINT(33) NOT NULL AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Surname VARCHAR(255) NOT NULL,
    InsertDate DATETIME,
    UpdateDate DATETIME,
    PRIMARY KEY (ID)
);
INSERT INTO Employee(ID, Name, Surname, InsertDate, UpdateDate) values 
(1, 'Mustafa', 'Nevfel', '1995/05/01', '1995/05/01'),
(2, 'Sedat', 'Demirkol', '1995/05/01', '1995/05/01');

# --- !Downs
DROP TABLE Employee

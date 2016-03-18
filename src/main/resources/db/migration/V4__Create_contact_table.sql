create TABLE Contact(
    ID int not null ,
    TYPE VARCHAR(200),
    VALUE VARCHAR(200),
    USER_ID int,
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) REFERENCES User(ID)
);


create TABLE Icard(
    ID int not null ,
    ICARDMSG VARCHAR(200) UNIQUE ,
    USER_ID int UNIQUE ,
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) REFERENCES User(ID)
);






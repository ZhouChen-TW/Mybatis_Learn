create TABLE User_Group(
    ID int not null ,
    USER_ID int not null,
    GROUP_ID int not null,
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) REFERENCES User(ID),
    FOREIGN KEY (GROUP_ID) REFERENCES MyGroup(ID)
    )


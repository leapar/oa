create table OA_RECORD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    LAT double precision,
    LNG double precision,
    DISTRICT varchar(512),
    PROVINCE varchar(255),
    ADDRESS varchar(1024),
    IP varchar(32),
    FILE_ID varchar(32),
    USER_ID varchar(32),
    --
    primary key (ID)
);

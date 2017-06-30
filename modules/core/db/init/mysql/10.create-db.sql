-- begin OA_AREA
create table OA_AREA (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end OA_AREA
-- begin OA_COMPANY
create table OA_COMPANY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end OA_COMPANY
-- begin OA_DEVICE_LOG
create table OA_DEVICE_LOG (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    DEVICE_NO varchar(255),
    USER_ID varchar(32),
    --
    primary key (ID)
)^
-- end OA_DEVICE_LOG
-- begin OA_RECORD
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
)^
-- end OA_RECORD
-- begin OA_USER_TYPE
create table OA_USER_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end OA_USER_TYPE
-- begin OA_USER_STATUS
create table OA_USER_STATUS (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end OA_USER_STATUS
-- begin SEC_USER
alter table SEC_USER add column TYPE_ID varchar(32) ^
alter table SEC_USER add column STATUS_ID varchar(32) ^
alter table SEC_USER add column COMPANY_ID varchar(32) ^
alter table SEC_USER add column AREA_ID varchar(32) ^
alter table SEC_USER add column DEVICE_NO varchar(255) ^
alter table SEC_USER add column API_TOKEN varchar(255) ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'oa$ExtUser' where DTYPE is null ^
-- end SEC_USER

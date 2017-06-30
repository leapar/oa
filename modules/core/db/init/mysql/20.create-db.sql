-- begin OA_DEVICE_LOG
alter table OA_DEVICE_LOG add constraint FK_OA_DEVICE_LOG_USER foreign key (USER_ID) references SEC_USER(ID)^
create index IDX_OA_DEVICE_LOG_USER on OA_DEVICE_LOG (USER_ID)^
-- end OA_DEVICE_LOG
-- begin OA_RECORD
alter table OA_RECORD add constraint FK_OA_RECORD_FILE foreign key (FILE_ID) references SYS_FILE(ID)^
alter table OA_RECORD add constraint FK_OA_RECORD_USER foreign key (USER_ID) references SEC_USER(ID)^
create index IDX_OA_RECORD_FILE on OA_RECORD (FILE_ID)^
create index IDX_OA_RECORD_USER on OA_RECORD (USER_ID)^
-- end OA_RECORD
-- begin SEC_USER
alter table SEC_USER add constraint FK_SEC_USER_TYPE foreign key (TYPE_ID) references OA_USER_TYPE(ID)^
create index IDX_SEC_USER_TYPE on SEC_USER (TYPE_ID)^
alter table SEC_USER add constraint FK_SEC_USER_STATUS foreign key (STATUS_ID) references OA_USER_STATUS(ID)^
create index IDX_SEC_USER_STATUS on SEC_USER (STATUS_ID)^
alter table SEC_USER add constraint FK_SEC_USER_COMPANY foreign key (COMPANY_ID) references OA_COMPANY(ID)^
create index IDX_SEC_USER_COMPANY on SEC_USER (COMPANY_ID)^
alter table SEC_USER add constraint FK_SEC_USER_AREA foreign key (AREA_ID) references OA_AREA(ID)^
create index IDX_SEC_USER_AREA on SEC_USER (AREA_ID)^
-- end SEC_USER

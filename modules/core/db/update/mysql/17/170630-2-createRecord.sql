alter table OA_RECORD add constraint FK_OA_RECORD_FILE foreign key (FILE_ID) references SYS_FILE(ID);
alter table OA_RECORD add constraint FK_OA_RECORD_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_OA_RECORD_FILE on OA_RECORD (FILE_ID);
create index IDX_OA_RECORD_USER on OA_RECORD (USER_ID);

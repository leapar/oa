alter table OA_RECORD add constraint FK_OA_RECORD_THUMB foreign key (THUMB_ID) references SYS_FILE(ID);
create index IDX_OA_RECORD_THUMB on OA_RECORD (THUMB_ID);

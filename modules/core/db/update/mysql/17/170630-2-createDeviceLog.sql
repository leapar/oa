alter table OA_DEVICE_LOG add constraint FK_OA_DEVICE_LOG_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_OA_DEVICE_LOG_USER on OA_DEVICE_LOG (USER_ID);

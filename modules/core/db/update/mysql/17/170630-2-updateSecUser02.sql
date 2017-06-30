alter table SEC_USER add constraint FK_SEC_USER_STATUS foreign key (STATUS_ID) references OA_USER_STATUS(ID);
create index IDX_SEC_USER_STATUS on SEC_USER (STATUS_ID);

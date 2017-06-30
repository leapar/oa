alter table SEC_USER add constraint FK_SEC_USER_TYPE foreign key (TYPE_ID) references OA_USER_TYPE(ID);
create index IDX_SEC_USER_TYPE on SEC_USER (TYPE_ID);

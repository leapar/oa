alter table SEC_USER add constraint FK_SEC_USER_AREA foreign key (AREA_ID) references OA_AREA(ID);
create index IDX_SEC_USER_AREA on SEC_USER (AREA_ID);

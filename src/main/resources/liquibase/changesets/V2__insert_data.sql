insert into users (name,username,password)
values ('admin','admin','$2a$10$SFzmSXHyHTd3GWSRb6ZbGume9M.xiPnlmJ5S.4rL4XtrIsrqRj1fW');


insert into worker (name,surname,status)
values ('gorbatenko','dmitry','$2a$10$SFzmSXHyHTd3GWSRb6ZbGume9M.xiPnlmJ5S.4rL4XtrIsrqRj1fW');

insert into vaccinations (worker_id,ADSM,HepatitisB,Measles,Rubella)
values ('1','ADSMv2','hepv2','measlesv2','rubella2');

insert into worker_vaccinations (worker_id,vaccinations_id)
values ('1','1');

insert into users_roles (users_id,roles)
values ('1','ROLE_ADMIN');

create schema IF not exists epid;


CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     name varchar(255) not null,
                                     username varchar(255) not null unique,
                                     password varchar(255) not null
);


CREATE TABLE IF NOT EXISTS worker (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      name varchar(255) not null,
                                      surname varchar(255) not null,
                                      status varchar(255) not null
);

CREATE TABLE IF NOT EXISTS vaccinations (
                                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            worker_id int unique,
                                            ADSM varchar(255) not null,
                                            HepatitisB varchar(255) not null,
                                            Measles varchar(255) not null,
                                            Rubella varchar(255) not null
);

CREATE TABLE IF NOT EXISTS worker_vaccinations (
                                                   worker_id bigint not null,
                                                   vaccinations_id bigint not null,
                                                   primary key (worker_id, vaccinations_id),
                                                   constraint fk_worker_vaccinations_worker foreign key (worker_id) references worker (id) on delete cascade on update no action,
                                                   constraint fk_worker_vaccinations_vaccinations foreign key (vaccinations_id) references vaccinations (id) on delete cascade on update no action
);

CREATE TABLE IF NOT EXISTS users_roles (
                                           users_id bigint not null,
                                           roles varchar(255) not null,
                                           primary key (users_id, roles),
                                           constraint fk_worker_vaccinations_users foreign key (users_id) references users (id) on delete cascade on update no action
);
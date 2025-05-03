CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS worker (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      surname VARCHAR(255) NOT NULL,
                                      status VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS vaccinations (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            worker_id INT UNIQUE,
                                            ADSM VARCHAR(255) NOT NULL,
                                            HepatitisB VARCHAR(255) NOT NULL,
                                            Measles VARCHAR(255) NOT NULL,
                                            Rubella VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS worker_vaccinations (   worker_id INT NOT NULL ,
                                                   vaccinations_id INT NOT NULL,
                                                   PRIMARY KEY (worker_id, vaccinations_id),
                                                   CONSTRAINT fk_worker_vaccinations_worker FOREIGN KEY (worker_id) references worker (id) on delete cascade on update no action,
                                                   CONSTRAINT fk_worker_vaccinations_vaccinations FOREIGN KEY (vaccinations_id) references vaccinations (id) on delete cascade on update no action
);
CREATE TABLE IF NOT EXISTS users_roles(    users_id INT       NOT NULL ,
    roles    VARCHAR(255) NOT NULL ,
    PRIMARY KEY (users_id, roles),
    CONSTRAINT fk_worker_vaccinations_users FOREIGN KEY key (users_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION
);
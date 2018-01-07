/**
 Author: Mrin
 Model : NorthWind
**/

DROP SCHEMA IF EXISTS dajt;

CREATE SCHEMA dajt;
USE dajt;

/* Table: folder */
CREATE TABLE folder (
    folder_id       INT NOT NULL AUTO_INCREMENT,
    number          VARCHAR(255),
    create_date     DATETIME ,
    modif_date      DATETIME ,
    close_date      DATETIME,
    folder_status   VARCHAR(50),
    offense         VARCHAR(255),
    court_id        INT,
    advocate_id     INT,
    judgment_id     INT,
    assigned        INT,
    PRIMARY KEY (folder_id)
);

/* Table: guilty */
CREATE TABLE guilty (
    guilty_id INT NOT NULL AUTO_INCREMENT,
    first_name NVARCHAR(20),
    last_name NVARCHAR (20),
    PRIMARY KEY (guilty_id)
);

/* Table: victim */
CREATE TABLE victim (
    victim_id INT NOT NULL AUTO_INCREMENT,
    first_name NVARCHAR(20),
    last_name NVARCHAR(20),
    PRIMARY KEY (victim_id)
);

CREATE TABLE court (
    court_id INT NOT NULL,
    name NVARCHAR(20),
    PRIMARY KEY (court_id)
);

/* Table: folder_guilty */
CREATE TABLE folder_guilty(
    folder_id INT NOT NULL,
    guilty_id INT NOT NULL
);

/* Table: folder_victim */
CREATE TABLE folder_victim(
    folder_id INT NOT NULL,
    victim_id INT NOT NULL
);

/* Table: advocate */
CREATE TABLE advocate (
    advocate_id     INT NOT NULL AUTO_INCREMENT,
    last_name       NVARCHAR(50),
    first_name      NVARCHAR(50),
    PRIMARY KEY (advocate_id)
);

/* Table judgment */
CREATE TABLE judgment (
    judgment_id     INT NOT NULL AUTO_INCREMENT,
    number          INT,
    judgment_date   DATETIME,
    judgment_status VARCHAR(50),
    folder_id       INT,
    PRIMARY KEY (judgment_id)
);

/* Table : Transmission */
CREATE TABLE transmission (
  transmission_id  INT NOT NULL AUTO_INCREMENT,
  work_done        VARCHAR(255) ,
  note             VARCHAR(255) ,
  send_number      VARCHAR(250),
  send_date        DATETIME,
  amount           INT,
  folder_id        INT,
  affair_id        INT,
  advocate_id      INT,
  judgment_id     INT,
  transmitted      BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (transmission_id)
);

/* Table : role  */
CREATE TABLE role (
    role_id INT NOT NULL  AUTO_INCREMENT,
    name NVARCHAR (20) NOT NULL,
    PRIMARY KEY (role_id)
);

/* Table: user (Application Users) */
CREATE TABLE user (
    user_id     INT NOT NULL,
    login       NVARCHAR(20) NOT NULL,
    password    NVARCHAR(20) NOT NULL,
    first_name  NVARCHAR(50) ,
    last_name   NVARCHAR(50) ,
    email       NVARCHAR(70) ,
    phone       NVARCHAR(20) ,
    address1    NVARCHAR(100),
    address2    NVARCHAR(100),
    country     NVARCHAR(20) ,
    postal      NVARCHAR(20) ,
    is_active   TINYINT  ,
    is_blocked  TINYINT  ,
    secret_question     NVARCHAR(100),
    secret_answer       NVARCHAR(100),
    CONSTRAINT user_id PRIMARY KEY(user_id)
);

/* Table : user_role */
CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id int NOT NULL
);

/* Foreign Key: folder */
ALTER TABLE folder ADD CONSTRAINT fk_folder__advocate FOREIGN KEY (advocate_id) REFERENCES advocate(advocate_id);
ALTER TABLE folder ADD CONSTRAINT fk_folder__judgment FOREIGN KEY (judgment_id) REFERENCES judgment(judgment_id);
ALTER TABLE folder ADD CONSTRAINT fk_folder__user FOREIGN KEY (user_id) REFERENCES user(user_id);

/* Foreign Key: folder_guilty */
ALTER TABLE folder_guilty ADD CONSTRAINT fk_folder__guilty FOREIGN KEY (folder_id) REFERENCES folder(folder_id);
ALTER TABLE folder_guilty ADD CONSTRAINT fk_guilty__folder FOREIGN KEY (guilty_id) REFERENCES guilty(guilty_id);

/* Foreign Key: folder_victim */
ALTER TABLE folder_victim ADD CONSTRAINT fk_folder__victim FOREIGN KEY (folder_id) REFERENCES folder(folder_id);
ALTER TABLE folder_victim ADD CONSTRAINT fk_victim__folder FOREIGN KEY (victim_id) REFERENCES victim(victim_id);

/* Foreign Key: judgment */
ALTER TABLE judgment ADD CONSTRAINT fk_judgment__folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id);

/* Foreign Key: user_role */
ALTER TABLE user_role ADD CONSTRAINT fk_user__role FOREIGN KEY (user_id) REFERENCES user(user_id);
ALTER TABLE user_role ADD CONSTRAINT fk_role__user FOREIGN KEY (role_id) REFERENCES role(role_id);


/* Foreign Key: transmission */
ALTER TABLE transmission ADD CONSTRAINT fk_transmission__folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id);
ALTER TABLE transmission ADD CONSTRAINT fk_transmission__advocate FOREIGN KEY (advocate_id) REFERENCES advocate(advocate_id);
ALTER TABLE transmission ADD CONSTRAINT fk_transmission__judgment FOREIGN KEY (judgment_id) REFERENCES judgment(judgment_id);

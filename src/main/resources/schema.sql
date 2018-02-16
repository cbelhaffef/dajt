DROP SCHEMA IF EXISTS dajt;

CREATE SCHEMA dajt;
USE dajt;

/* Table: user (Application Users) */
CREATE TABLE user (
    user_id     INT NOT NULL,
    username    NVARCHAR(20) NOT NULL,
    password    NVARCHAR(20) NOT NULL,
    first_name  NVARCHAR(50) ,
    last_name   NVARCHAR(50) ,
    email       NVARCHAR(70) ,
    security_provider_id INT ,
    default_customer_id  INT ,
    company     NVARCHAR(50) ,
    phone       NVARCHAR(20) ,
    address1    NVARCHAR(100),
    address2    NVARCHAR(100),
    country     NVARCHAR(20) ,
    postal      NVARCHAR(20) ,
    role        NVARCHAR(20) ,
    other_roles NVARCHAR(80) ,
    is_active   TINYINT  ,
    is_blocked  TINYINT  ,
    secret_question     NVARCHAR(100),
    secret_answer       NVARCHAR(100),
    enable_beta_testing TINYINT,
    enable_renewal      TINYINT,
    CONSTRAINT user_id PRIMARY KEY(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;;

/* Table : role  */
CREATE TABLE role (
    role_id INT NOT NULL  AUTO_INCREMENT,
    name NVARCHAR (255) NOT NULL,
    PRIMARY KEY (role_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table : user_role */
CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id int NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    /*KEY `role_id` (`role_id`),*/
    CONSTRAINT fk_user__role FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT fk_role__user FOREIGN KEY (role_id) REFERENCES role(role_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: advocate */
CREATE TABLE advocate (
    advocate_id     INT NOT NULL AUTO_INCREMENT,
    name       NVARCHAR(255),
    PRIMARY KEY (advocate_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE court (
    court_id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(255),
    PRIMARY KEY (court_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE office (
    office_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (office_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;;

/* Table: folder */
CREATE TABLE folder (
    folder_id       INT NOT NULL AUTO_INCREMENT,
    number          VARCHAR(255),
    create_date     DATETIME ,
    last_modif_date DATETIME ,
    close_date      DATETIME,
    folder_status   VARCHAR(50),
    offence         VARCHAR(255),
    judgement_date  DATETIME,
    judgement_status VARCHAR(255),
    court_id        INT,
    advocate_id     INT,
    user_id         INT,
    office_id       INT,
    CONSTRAINT fk_folder__court    FOREIGN KEY (court_id)    REFERENCES court(court_id),
    CONSTRAINT fk_folder__advocate FOREIGN KEY (advocate_id) REFERENCES advocate(advocate_id),
    CONSTRAINT fk_folder__user     FOREIGN KEY (user_id)     REFERENCES user(user_id),
    CONSTRAINT fk_folder__office   FOREIGN KEY (office_id)   REFERENCES office(office_id),
    PRIMARY KEY (folder_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: guilty */
CREATE TABLE guilty (
    guilty_id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(255),
    PRIMARY KEY (guilty_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: victim */
CREATE TABLE victim (
    victim_id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(255),
    PRIMARY KEY (victim_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: folder_guilty */
CREATE TABLE folder_guilty(
    folder_id INT NOT NULL,
    guilty_id INT NOT NULL,
    PRIMARY KEY (`folder_id`,`guilty_id`),
   /* KEY `guilty_id` (`guilty_id`),*/
    CONSTRAINT fk_folder__guilty FOREIGN KEY (folder_id) REFERENCES folder(folder_id),
    CONSTRAINT fk_guilty__folder FOREIGN KEY (guilty_id) REFERENCES guilty(guilty_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: folder_victim */
CREATE TABLE folder_victim(
    folder_id INT NOT NULL,
    victim_id INT NOT NULL,
    PRIMARY KEY (`folder_id`,`victim_id`),
    /*KEY `guilty_id` (`victim_id`),*/
    CONSTRAINT fk_folder__victim FOREIGN KEY (folder_id) REFERENCES folder(folder_id),
    CONSTRAINT fk_victim__folder FOREIGN KEY (victim_id) REFERENCES victim(victim_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table : Transmission */
CREATE TABLE transmission (
  transmission_id  INT NOT NULL AUTO_INCREMENT,
  work_done        VARCHAR(255) ,
  note             VARCHAR(255) ,
  send_number      VARCHAR(250),
  send_date        DATETIME,
  amount           INT,
  folder_id        INT,
  transmitted      BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_transmission__folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id),
  PRIMARY KEY (transmission_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

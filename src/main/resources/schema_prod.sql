SET FOREIGN_KEY_CHECKS = 0;
SET @tables = NULL;
SELECT GROUP_CONCAT(table_schema, '.', table_name) INTO @tables
FROM information_schema.tables
WHERE table_schema = 'heroku_6c441d064410a7d'; -- specify DB name here.

SET @tables = CONCAT('DROP TABLE ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE office (
    office_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (office_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: user (Application Users) */
CREATE TABLE user (
    user_id     INT NOT NULL,
    username    NVARCHAR(20) NOT NULL,
    password    NVARCHAR(20) NOT NULL,
    first_name  NVARCHAR(50) ,
    last_name   NVARCHAR(50) ,
    email       NVARCHAR(70) ,
    role        NVARCHAR(20) ,
    sex         NVARCHAR(20) ,
    office_id   INT ,
    is_active   TINYINT  ,
    CONSTRAINT fk_user__office FOREIGN KEY (office_id) REFERENCES office(office_id),
    CONSTRAINT user_id PRIMARY KEY(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

/* Table: folder */
CREATE TABLE folder (
    folder_id       INT NOT NULL AUTO_INCREMENT,
    number           VARCHAR(255) NOT NULL UNIQUE,
    directorate_key VARCHAR(255) UNIQUE,
    offence         VARCHAR(255) NOT NULL,
    offence_date    TIMESTAMP,
    office_id       INT NOT NULL,
    court_id        INT NOT NULL,
    administration_concerned LONGTEXT NOT NULL,
    assignee        INT,
    reporter        INT,
    created         DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    closed          TIMESTAMP,
    judged          TIMESTAMP,
    judgement_status VARCHAR(255),
    folder_status   VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    folder_priority VARCHAR(50) NOT NULL DEFAULT 'MINOR',
    advocate_id     INT,
    CONSTRAINT fk_folder__office   FOREIGN KEY (office_id)   REFERENCES office(office_id),
    CONSTRAINT fk_folder__court    FOREIGN KEY (court_id)    REFERENCES court(court_id),
    CONSTRAINT fk_folder__advocate FOREIGN KEY (advocate_id) REFERENCES advocate(advocate_id),
    CONSTRAINT fk_folder__assignee     FOREIGN KEY (assignee)   REFERENCES user(user_id),
    CONSTRAINT fk_folder__reporter     FOREIGN KEY (reporter)   REFERENCES user(user_id),
    PRIMARY KEY (folder_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: accused */
CREATE TABLE accused (
    accused_id INT NOT NULL AUTO_INCREMENT,
    name       NVARCHAR(255),
    folder_id  INT,
    CONSTRAINT fk_accused__folder   FOREIGN KEY (folder_id)   REFERENCES folder(folder_id),
    PRIMARY KEY (accused_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: victim */
CREATE TABLE victim (
    victim_id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(255),
    folder_id  INT,
    CONSTRAINT fk_victim__folder   FOREIGN KEY (folder_id)   REFERENCES folder(folder_id),
    PRIMARY KEY (victim_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: action */
CREATE TABLE action (
    action_id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(255),
    PRIMARY KEY (action_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table: folder_action */
CREATE TABLE folder_action(
    folder_id INT NOT NULL,
    action_id INT NOT NULL,
    PRIMARY KEY (`folder_id`,`action_id`),
    CONSTRAINT fk_folder__action FOREIGN KEY (folder_id) REFERENCES folder(folder_id),
    CONSTRAINT fk_action__folder FOREIGN KEY (action_id) REFERENCES action(action_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table : Transmission */
CREATE TABLE transmission (
    transmission_id  INT NOT NULL AUTO_INCREMENT,
    work_done        VARCHAR(255) ,
    note             VARCHAR(255) ,
    send_number      VARCHAR(250),
    send_date        TIMESTAMP,
    amount           INT,
    folder_id        INT,
    transmitted      BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_transmission__folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id),
    PRIMARY KEY (transmission_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Table : Import */
CREATE TABLE `import` (
    imp_id       bigint(20) NOT NULL AUTO_INCREMENT,
    start_date   DATETIME DEFAULT NULL,
    end_date     DATETIME DEFAULT NULL,
    process_date DATETIME DEFAULT NULL,
    status       varchar(45) DEFAULT NULL,
    message      longtext,
    file_name    varchar(256) DEFAULT NULL,
    file_size    bigint(20) DEFAULT NULL,
    PRIMARY KEY (imp_id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/* Table : Import */
CREATE TABLE `comment` (
    comment_id   bigint(20) NOT NULL AUTO_INCREMENT,
    created      DATETIME DEFAULT NULL,
    updated      DATETIME DEFAULT NULL,
    body           LONGTEXT DEFAULT NULL,
    author         INT,
    author_updated INT,
    folder_id      INT,
    CONSTRAINT fk_comment__folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id),
    CONSTRAINT fk_comment__user FOREIGN KEY (author) REFERENCES user(user_id),
    CONSTRAINT fk_comment__user_updated FOREIGN KEY (author_updated) REFERENCES user(user_id),
    PRIMARY KEY (comment_id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

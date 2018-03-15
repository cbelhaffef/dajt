/*SET NAMES 'utf8' COLLATE 'utf8_general_ci';*/

insert into court(court_id,`name`) values
    (1 , 'عبادلـة'),
    (2 , 'سعيـدة'),
    (3 , 'سعيـدة'),
    (4 , 'الحـراش'),
    (5 , 'سعيـدة'),
    (6 , 'غريـس');

insert into office(office_id, name) VALUES
    (1, 'مكتب 1'),
    (2, 'مكتب 2');

/* User */
insert into user (user_id, username,password, first_name, last_name, email, company, phone, address1, address2, country, postal, role, is_active, is_blocked, security_provider_id, default_customer_id, secret_question, secret_answer, enable_beta_testing, enable_renewal) values
    (1,'demo'      , 'demo'     , 'demo'  , 'demo', 'arivera2@joomla.org'    , 'Abshire Inc', '7-(740)701-4547', '80429 Garrison Crossing', '4967'               , 'USA'        , '64890', 'USER' , 1, 0, 10001, 20000, 'Diverse'       , 'Yellow' , 0, 0),
    (2,'admin'     , 'admin'    , 'Theresa'  , 'Russell' , 'trussell1@about.me'     , 'Glover, Adams and Bins', '383-(779)851-3208', '30874 Graceland Terrace', '99152' , 'USA'        , '51065', 'ADMIN', 1, 0, 10001, 20000, 'knowledge base', 'Mauv'   , 1, 0);

/* Folders */
insert into folder (folder_id ,`number` ,folder_status , folder_priority, create_date ,offence ,court_id ,office_id , assignee,reporter, sending_type) values
    (1, '13/01 bis' , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة' ,2 , 1, 1,1,'م ع أ و n\ رقم الملف 2012/95'),
    (2, '13/02'     , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة' ,3 , 1 , null,null,'م ع أ و n\ رقم الملف 2012/95'),
    (3, '13/03'     , 'OPEN' ,'MINOR' , '2017-05-12' ,'اهانــــة' ,4 , 1 ,null ,null,'م ع أ و n\ رقم الملف 2012/95'),
    (4, '13/04'     , 'OPEN' ,'MINOR' , '2017-06-20' ,'إهانـة هيئة نظاميـة بالإبلاغ عن جريمة يعلم بعدم وقوعها',5 ,1 ,null ,null,'م ع أ و n\ رقم الملف 2012/95'),
    (5, '13/05'     , 'OPEN' ,'MINOR' , '2017-05-19' ,'إهانـة هيئة نظاميـة و السب المتبادل'                 ,6 ,1 ,null ,null,'م ع أ و n\ رقم الملف 2012/95');

insert into guilty(guilty_id, name) values
    (1, 'بسايـح يحـي'),
    (2, 'بسايـح عبد الناصـر'),
    (3,   'عراب مختـار'),
    (4,  'شعيـب سالـم'),
    (5, 'دريـس أحمـد'),
    (6, 'فلاح فاطمـة'),
    (7, 'براهيمـي عبد اللطيـف'),
    (8,  'فخـات زهـرة'),
    (9,'بن عطية مكيـة'),
    (10,'بن عودة مختارية'),
    (11,'رهواج فاطمةالزهراء');

insert into victim(victim_id, name) values
    (1,'ق خ و'),
    (2, 'م ط');

insert into folder_guilty(folder_id,guilty_id) values
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (3, 5),
    (4, 6),
    (5, 7);

insert into folder_victim(folder_id,victim_id) values
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1),
    (3, 2),
    (4, 1),
    (5, 1);




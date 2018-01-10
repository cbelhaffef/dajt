/* Folders */
insert into folder (folder_id, `number`, folder_status, create_date, offence,court_id) values
    (1, '01/13'     , 'OPEN' , '2017-12-31', 'إهانـة رجال القوة العمومية',1),
    (2, '01/13 bis' , 'OPEN' , '2017-07-30','اهانــــة',2),
    (3, '02/13'     , 'OPEN' , '2017-07-30','اهانــــة',3),
    (4, '03/13'     , 'OPEN' , '2017-05-12','اهانــــة',4),
    (5, '04/13'     , 'OPEN' , '2017-06-20','إهانـة هيئة نظاميـة بالإبلاغ عن جريمة يعلم بعدم وقوعها',5),
    (6, '05/13'     , 'OPEN' , '2017-05-19','إهانـة هيئة نظاميـة و السب المتبادل',6);


insert into guilty(guilty_id, first_name ,last_name) values
    (1, 'بسايـح', 'يحـي'),
    (2, 'بسايـح', 'عبد الناصـر'),
    (3, 'عراب' , 'مختـار'),
    (4, 'شعيـب' ,'سالـم'),
    (5, 'دريـس' , 'أحمـد'),
    (6, 'فلاح' , 'فاطمـة'),
    (7, 'براهيمـي' ,'عبد اللطيـف'),
    (8, 'فخـات' , 'زهـرة'),
    (9, 'بن عطية' ,'مكيـة'),
    (10, 'بن عودة' ,'مختارية'),
    (11, 'رهواج' ,'فاطمةالزهراء');

insert into victim(victim_id, first_name, last_name) values
    (1,'ق.خ','و'),
    (2,  'م', 'ط');

insert into court(court_id,`name`) values
    (1 , 'عبادلـة'),
    (2 , 'سعيـدة'),
    (3 , 'سعيـدة'),
    (4 , 'الحـراش'),
    (5 , 'سعيـدة'),
    (6 , 'غريـس');

/* User */
insert into user (user_id, login,password, first_name, last_name, email, company, phone, address1, address2, country, postal, role, is_active, is_blocked, security_provider_id, default_customer_id, secret_question, secret_answer, enable_beta_testing, enable_renewal) values
(1,'demo'      , 'demo'     , 'demo'  , 'demo', 'arivera2@joomla.org'    , 'Abshire Inc', '7-(740)701-4547', '80429 Garrison Crossing', '4967'               , 'USA'        , '64890', 'USER' , 1, 0, 10001, 20000, 'Diverse'       , 'Yellow' , 0, 0),
(2,'admin'     , 'admin'    , 'Theresa'  , 'Russell' , 'trussell1@about.me'     , 'Glover, Adams and Bins', '383-(779)851-3208', '30874 Graceland Terrace', '99152' , 'USA'        , '51065', 'ADMIN', 1, 0, 10001, 20000, 'knowledge base', 'Mauv'   , 1, 0);

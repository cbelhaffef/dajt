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

insert into action(action_id, name) VALUES
    (1, 'Consitiution des Avocats'),
    (2, 'Suites Réservées'),
    (3, 'Suites Réservées (Avocats)'),
    (4, 'Demandes de jugement'),
    (5, 'Demandes de jugement (Avocats)'),
    (6, 'Demandes de Grosses'),
    (7, 'Demandes de Citations'),
    (8, 'Notifications'),
    (9, 'Demandes d\'executions'),
    (10, 'Notes d\'honoraires'),
    (11, 'Classé Sans Suite'),
    (12, 'Actions Civiles'),
    (13, 'Demande de pièces justificatifs'),
    (14, 'Demande d\'emission d\'un état exécutaire');


/* User */
insert into user
    (user_id, username,           password,              first_name,       last_name,                 email,                role,    is_active,   sex,      office_id) values
    (1,       'admin',            'admin',               'admin'         ,  'admin',             'admin@dajt.gouv.dz',      'ADMIN' ,   1,        null,      null),
    (2,       'hkharbouche',      'changeme',            'houria'       ,  'kharbouche',        'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  null),
    (3,       'souala',          'changeme',            'soumia'       ,  'ouala',              'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  2),
    (4,       'caouina',          'changeme',            'chafia'       ,  'aouina',            'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  2),
    (5,       'kbenzaied',        'changeme',            'kenza'        ,  'benzaied',          'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  1),
    (6,       'alamara',          'changeme',            'ahmed'        ,  'lamara',            'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    1),
    (7,       'abelhaffef',       'changeme',            'abdelaziz'    ,  'belhaffef',         'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    1),
    (8,       'rderrasechouk',    'changeme',            'rahima'       ,  'derras echouk',     'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  1),
    (9,       'faffifchaouche',   'changeme',            'farid'        ,  'affif chaouche',    'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    2),
    (10,       'mtabel',           'changeme',            'mourad'        ,  'tabel',            'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    2),
    (11,       'omohamedbanir',    'changeme',            'omar'        ,  'mohamed banir',      'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    2);

/* Folders */
insert into folder (folder_id ,`number` ,folder_status , folder_priority, create_date ,offence ,court_id ,office_id , assignee,reporter, sending_type) values
    (1, '13/01 bis' , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة' ,2 , 1, 1,1,'م ع أ و <br> رقم الملف 2012/95'),
    (2, '13/02'     , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة' ,3 , 1 , null,null,'م ع أ و <br> رقم الملف 2012/95'),
    (3, '13/03'     , 'OPEN' ,'MINOR' , '2017-05-12' ,'اهانــــة' ,4 , 1 ,null ,null,'م ع أ و <br> رقم الملف 2012/95'),
    (4, '13/04'     , 'OPEN' ,'MINOR' , '2017-06-20' ,'إهانـة هيئة نظاميـة بالإبلاغ عن جريمة يعلم بعدم وقوعها',5 ,1 ,null ,null,'م ع أ و <br> رقم الملف 2012/95'),
    (5, '13/05'     , 'OPEN' ,'MINOR' , '2017-05-19' ,'إهانـة هيئة نظاميـة و السب المتبادل'                 ,6 ,1 ,null ,null,'م ع أ و <br> رقم الملف 2012/95');

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




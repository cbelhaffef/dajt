insert into office(office_id, name) VALUES
    (1, 'مكتب 1'),
    (2, 'مكتب 2');

# insert into court(court_id,`name`) values
#     (1 , 'عبادلـة'),
#     (2 , 'سعيـدة'),
#     (3 , 'قسنطينـة'),
#     (4 , 'الحـراش'),
#     (5 , 'ورقلـة'),
#     (6 , 'باب الـوادي');

insert into status(status_id, code, name,icon) VALUES
    (1,'OPEN','مفتوح','../../../assets/images/status/open.png'),
    (2,'IN_PROGRESS','جاري','../../../assets/images/status/in_progress.png'),
    (3,'AWAiTING_SIGNATURE','في انتظار التوقيع','../../../assets/images/status/awaiting_signature.png'),
    (4,'CLOSE','مغلق','../../../assets/images/status/close.svg');

insert into priority(priority_id, code, name,icon) VALUES
    (1,'MINOR','ثانوي','../../../assets/images/priorities/minir.svg'),
    (2,'MAJOR','اولوي','../../../assets/images/priorities/major.svg'),
    (23,'URGENT','عاجل','../../../assets/images/priorities/urgent.svg');


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
    (user_id, username,           password,              firstname,       lastname,                 email,                role,    is_active,   sex,      office_id) values
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
# insert into folder (folder_id ,number ,folder_status , folder_priority, created ,offence ,court_id ,office_id , assignee,reporter, administration_concerned ) values
#     (1, 'bis 01/13' , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة'                               ,2 , 1, 1,1,'م ع أ و <br> رقم الملف 2012/95'),
#     (2, '01/13'     , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة'                               ,2 , 1, 1,1,'م ع أ و <br> رقم الملف 2012/95'),
#     (3, '02/13'     , 'OPEN' ,'MINOR' , '2017-07-30' ,'اهانــــة'                               ,3 , 1 , null,null,'م ع أ و <br> رقم الملف 2012/95'),
#     (4, '03/13'     , 'OPEN' ,'MINOR' , '2017-05-12' ,'اهانــــة'                               ,4 , 1 ,null ,null,'م ع أ و <br> رقم الملف 2012/95'),
#     (5, '04/13'     , 'OPEN' ,'MINOR' , '2017-06-20' ,'إهانـة هيئة نظاميـة بالإبلاغ عن جريمة يعلم بعدم وقوعها',5 ,1 ,null ,null,'م ع أ و <br> رقم الملف 2012/95'),
#     (6, '05/13'     , 'OPEN' ,'MINOR' , '2017-05-19' ,'إهانـة هيئة نظاميـة و السب المتبادل'     ,6 ,1 ,null ,null,'م ع أ و <br> رقم الملف 2012/95');


# insert into accused(accused_id, name, folder_id) values
#     (1, 'بسايـح يحـي', 1),
#     (2, 'بسايـح عبد الناصـر', 2),
#     (3, 'عراب مختـار', 2),
#     (4, 'شعيـب سالـم' , 3),
#     (5, 'دريـس أحمـد', 3),
#     (6, 'فلاح فاطمـة', 4),
#     (7, 'براهيمـي عبد اللطيـف', 4),
#     (8, 'فخـات زهـرة', 4),
#     (9, 'بن عطية مكيـة'  , 5),
#     (10, 'بن عودة مختارية', 5),
#     (11, 'رهواج فاطمةالزهراء',5);

# insert into victim(victim_id, name, folder_id) values
#     (1,'ق خ و', 1),
#     (2, 'م ط', 2),
#     (3, 'فلاح مكيـة', 2),
#     (4, 'بن عطية فاطمـة' , 3),
#     (5, 'فاطمـة دريـس', 4),
#     (6, 'أحمـد فلاح', 5);




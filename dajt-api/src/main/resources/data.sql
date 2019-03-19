insert into office(office_id, name) VALUES
    (1, 'مكتب 1'),
    (2, 'مكتب 2');

insert into status(status_id, code, name,icon) VALUES
    (1,'OPEN',                          'مفتوح', '../../../assets/images/status/open.png'),
    (2,'IN_PROGRESS',                    'جاري', '../../../assets/images/status/in_progress.png'),
    (3,'AWAiTING_SIGNATURE','في انتظار التوقيع', '../../../assets/images/status/awaiting_signature.png'),
    (4,'CLOSE',                          'مغلق', '../../../assets/images/status/close.png');

insert into priority(priority_id, code, name,icon) VALUES
    (1,'MINOR', 'ثانوي', '../../../assets/images/priorities/minor.svg'),
    (2,'MAJOR', 'اولوي', '../../../assets/images/priorities/major.svg'),
    (3,'URGENT', 'عاجل', '../../../assets/images/priorities/urgent.svg');


insert into action(action_id, code, name, description) VALUES
    (1,  'CONSTITUTION_LIST_ADVOCATS',           'إنشاء قائمة المحامين', 'Consititution des avocats'),
    (2,  'RESERVER_SUITES',                              'أجنحة محجوزة', 'Suites Réservées'),
    (3,  'RESERVER_ADVOCATS',                   'أجنحة محجوزة (محامون)', 'Suites Réservées (Avocats)'),
    (4,  'JUDGMENT_REQUESTS',                             'طلبات الحكم', 'Demandes de jugement'),
    (5,  'JUDGMENT_REQUESTS_ADVOCATS',         'طلبات الحكم (المحامون)', 'Demandes de jugement (Avocats)'),
    (6,  'GROSSES_REQUESTS',                              'طلبات النسخ', 'Demandes de Grosses'),
    (7,  'CITATIONS_REQUESTS',               'طلبات الاستدعاءات للمحكمة', 'Demandes de Citations'),
    (8,  'NOTIFICATIONS',                                      'إعلامات', 'Notifications'),
    (9,  'EXECUTIONS_REQUESTS',                       'طلبات التطبيقات', 'Demandes d\'executions'),
    (10, 'HONORARY_NOTE',                            'فاتورات التكاليف', 'Notes d\'honoraires'),
    (11, 'RANKED_NO_SUITE',                         'مصنف بدون إستمرار', 'Classé Sans Suite'),
    (12, 'CIVIL_ACTIONS',                                 'أعمال مدنية', 'Actions Civiles'),
    (13, 'REQUEST_SUPPORTING_DOCUMENTS',          'طلب الوثائق الداعمة', 'Demande de pièces justificatives'),
    (14, 'REQUEST_ISSUANCE_BINDING_STATE', 'طلب إصدار بيان قابل للإنفاذ', 'Demande d\'emission d\'un état exécutoire');

insert into role(role_id, name, code) values
    (1, 'إجرائي', 'ROLE_ADMIN'),
    (2, 'وكيل',   'ROLE_USER');

/* User */
insert into user
    (user_id, username,           password,                                                                   firstname,        lastname,            email,                         enabled,   sex,      office_id) values
    (1,       'admin',            '$2a$10$F.QTzKbl1Pwb9qf2BWg/5O.0HyugZ20S54mX0YX4us2fNSjVJtcA6',               'admin',          'admin',             'admin@dajt.gouv.dz',          true,      null,     1),
    (2,       'agent',            '$2a$10$4SwvxQFk0/UFlg1ZTGPuX.DwMF.vdSaPiJVc.lmwkpFxGd4atIvOu',                'agent',          'agent',             'agent@dajt.gouv.dz',          true,      'MALE',    1);
/*
    (2,       'hkharbouche',      'changeme',            'houria'       ,  'kharbouche',        'admin@dajt.gouv.dz',      2,        true,     'MALE'  1);
    (3,       'souala',           'changeme',            'soumia'       ,  'ouala',             'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  2),
    (4,       'caouina',          'changeme',            'chafia'       ,  'aouina',            'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  2),
    (5,       'kbenzaied',        'changeme',            'kenza'        ,  'benzaied',          'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  1),
    (6,       'alamara',          'changeme',            'ahmed'        ,  'lamara',            'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    1),
    (7,       'abelhaffef',       'changeme',            'abdelaziz'    ,  'belhaffef',         'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    1),
    (8,       'rderrasechouk',    'changeme',            'rahima'       ,  'derras echouk',     'admin@dajt.gouv.dz',       'USER' ,    1,        'FEMALE',  1),
    (9,       'faffifchaouche',   'changeme',            'farid'        ,  'affif chaouche',    'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    2),
    (10,      'mtabel',           'changeme',            'mourad'       ,  'tabel',             'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    2),
    (11,      'omohamedbanir',    'changeme',            'omar'         ,  'mohamed banir',     'admin@dajt.gouv.dz',       'USER' ,    1,        'MALE',    2);
*/

insert into user_role(user_id, role_id) values
   (1, 1),
   (1, 2),
   (2, 2);

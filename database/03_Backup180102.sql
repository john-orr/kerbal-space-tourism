UPDATE TOURIST_ITINERARY SET PREREQUISITE=null;
DELETE FROM TOURIST_ITINERARY;
DELETE FROM MISSION;
DELETE FROM VESSEL;
DELETE FROM FLIGHT;
DELETE FROM TOURIST;

INSERT INTO TOURIST VALUES('CALDEN',  'CHIVALROUS');
INSERT INTO TOURIST VALUES('FERMORE', 'CHIVALROUS');
INSERT INTO TOURIST VALUES('GILBLE',  'MUTE');
INSERT INTO TOURIST VALUES('GUSCAL',  'MUTE');
INSERT INTO TOURIST VALUES('MILGAN',  'KERBIN');
INSERT INTO TOURIST VALUES('SUFEN',   'KERBIN');
INSERT INTO TOURIST VALUES('TIBIN',   'CHIVALROUS');
INSERT INTO TOURIST VALUES('VALLAN',  'KERBIN');
INSERT INTO TOURIST VALUES('VALNER',  'MUTE');

INSERT INTO FLIGHT VALUES(3,  'CHIVALROUS', 'KERBIN',     null,     3);
INSERT INTO FLIGHT VALUES(1,  'CHIVALROUS', 'KERBIN',     'MINMUS', 3);
INSERT INTO FLIGHT VALUES(10, 'CHIVALROUS', 'MUN',        null,     2);
INSERT INTO FLIGHT VALUES(4,  'CHIVALROUS', 'MUTE',       null,     3);
INSERT INTO FLIGHT VALUES(2,  'KERBIN',     'CHIVALROUS', null,     3);
INSERT INTO FLIGHT VALUES(9,  'KERBIN',     'KERBIN',     'MINMUS', 3);
INSERT INTO FLIGHT VALUES(12, 'KERBIN',     'KERBIN',     'MUN',    3);
INSERT INTO FLIGHT VALUES(6,  'KERBIN',     'MUTE',       null,     3);
INSERT INTO FLIGHT VALUES(8,  'MINMUS',     'MUTE',       null,     2);
INSERT INTO FLIGHT VALUES(11, 'MUN',        'CHIVALROUS', null,     2);
INSERT INTO FLIGHT VALUES(5,  'MUTE',       'KERBIN',     null,     3);
INSERT INTO FLIGHT VALUES(7,  'MUTE',       'MINMUS',     null,     2);

INSERT INTO VESSEL VALUES ('ADORABLE',    'MUTE',       2);
INSERT INTO VESSEL VALUES ('EDUCATED',    'CHIVALROUS', 3);
INSERT INTO VESSEL VALUES ('RECEPTIVE',   'MUTE',       3);
INSERT INTO VESSEL VALUES ('SYNONYMOUS',  'CHIVALROUS', 2);

-- NO MISSIONS

INSERT INTO TOURIST_ITINERARY VALUES (69,  'VALNER',   5,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (80,  'GILBLE',   5,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (83,  'GUSCAL',   7,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (84,  'GUSCAL',   8,  83,     null);
INSERT INTO TOURIST_ITINERARY VALUES (85,  'GUSCAL',   5,  84,     null);
INSERT INTO TOURIST_ITINERARY VALUES (89,  'FERMORE',  1,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (91,  'TIBIN',    1,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (93,  'CALDEN',   3,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (108, 'MILGAN',   6,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (109, 'MILGAN',   5,  108,    null);
INSERT INTO TOURIST_ITINERARY VALUES (110, 'SUFEN',    2,  null,   null);
INSERT INTO TOURIST_ITINERARY VALUES (111, 'SUFEN',    10, 110,    null);
INSERT INTO TOURIST_ITINERARY VALUES (112, 'SUFEN',    11, 111,    null);
INSERT INTO TOURIST_ITINERARY VALUES (113, 'SUFEN',    4,  112,    null);
INSERT INTO TOURIST_ITINERARY VALUES (114, 'SUFEN',    7,  113,    null);
INSERT INTO TOURIST_ITINERARY VALUES (115, 'SUFEN',    8,  114,    null);
INSERT INTO TOURIST_ITINERARY VALUES (116, 'SUFEN',    5,  115,    null);
INSERT INTO TOURIST_ITINERARY VALUES (117, 'VALLAN',   12, null,   null);

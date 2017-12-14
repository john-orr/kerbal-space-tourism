UPDATE TOURIST_ITINERARY SET PREREQUISITE=null;
DELETE FROM TOURIST_ITINERARY;
DELETE FROM MISSION;
DELETE FROM VESSEL;
DELETE FROM FLIGHT;
DELETE FROM TOURIST;

INSERT INTO TOURIST VALUES('ARCHIBALD', 'KERBIN');
INSERT INTO TOURIST VALUES('GILBLE', 'CHIVALROUS');
INSERT INTO TOURIST VALUES('GUSCAL', 'CHIVALROUS');
INSERT INTO TOURIST VALUES('JEBGER', 'KERBIN');
INSERT INTO TOURIST VALUES('LALEY', 'DEFEATED');
INSERT INTO TOURIST VALUES('MIDOUS', 'SYNONYMOUS');
INSERT INTO TOURIST VALUES('NELWIG', 'DEFEATED');
INSERT INTO TOURIST VALUES('PHOCHEL', 'DEFEATED');
INSERT INTO TOURIST VALUES('RORIM', 'SYNONYMOUS');
INSERT INTO TOURIST VALUES('TAMTHA', 'CHIVALROUS');
INSERT INTO TOURIST VALUES('VALNER', 'CHIVALROUS');

INSERT INTO FLIGHT VALUES(3,  'CHIVALROUS', 'KERBIN',     null,     3);
INSERT INTO FLIGHT VALUES(1,  'CHIVALROUS', 'KERBIN',     'MINMUS', 3);
INSERT INTO FLIGHT VALUES(10, 'CHIVALROUS', 'MUN',        null,     2);
INSERT INTO FLIGHT VALUES(4,  'CHIVALROUS', 'MUTE',       null,     3);
INSERT INTO FLIGHT VALUES(2,  'KERBIN',     'CHIVALROUS', null,     3);
INSERT INTO FLIGHT VALUES(9,  'KERBIN',     'KERBIN',     'MINMUS', 3);
INSERT INTO FLIGHT VALUES(6,  'KERBIN',     'MUTE',       null,     3);
INSERT INTO FLIGHT VALUES(8,  'MINMUS',     'MUTE',       null,     2);
INSERT INTO FLIGHT VALUES(11, 'MUN',        'CHIVALROUS', null,     2);
INSERT INTO FLIGHT VALUES(5,  'MUTE',       'KERBIN',     null,     3);
INSERT INTO FLIGHT VALUES(7,  'MUTE',       'MINMUS',     null,     2);

INSERT INTO VESSEL VALUES ('ADORABLE',    'MUTE',           2);
INSERT INTO VESSEL VALUES ('SYNONYMOUS',  'CHIVALROUS_DEP', 2);
INSERT INTO VESSEL VALUES ('DEFEATED',    null,             3);
INSERT INTO VESSEL VALUES ('SUBSTANTIAL', 'CHIVALROUS',     3);
INSERT INTO VESSEL VALUES ('RECEPTIVE',   'CHIVALROUS',     3);

INSERT INTO MISSION VALUES (1, 4,  'DEFEATED',   'ACTIVE');
INSERT INTO MISSION VALUES (2, 10, 'SYNONYMOUS', 'READY');

INSERT INTO TOURIST_ITINERARY VALUES (70, 'ARCHIBALD', 6,  null, null);
INSERT INTO TOURIST_ITINERARY VALUES (71, 'ARCHIBALD', 5,  70,   null);
INSERT INTO TOURIST_ITINERARY VALUES (77, 'GILBLE',    10, null, null);
INSERT INTO TOURIST_ITINERARY VALUES (78, 'GILBLE',    11, 77,   null);
INSERT INTO TOURIST_ITINERARY VALUES (79, 'GILBLE',    4,  78,   null);
INSERT INTO TOURIST_ITINERARY VALUES (80, 'GILBLE',    5,  79,   null);
INSERT INTO TOURIST_ITINERARY VALUES (82, 'GUSCAL',    4,  null, null);
INSERT INTO TOURIST_ITINERARY VALUES (83, 'GUSCAL',    7,  82,   null);
INSERT INTO TOURIST_ITINERARY VALUES (84, 'GUSCAL',    8,  83,   null);
INSERT INTO TOURIST_ITINERARY VALUES (85, 'GUSCAL',    5,  84,   null);
INSERT INTO TOURIST_ITINERARY VALUES (61, 'JEBGER',    6,  null, null);
INSERT INTO TOURIST_ITINERARY VALUES (62, 'JEBGER',    7,  61,   null);
INSERT INTO TOURIST_ITINERARY VALUES (63, 'JEBGER',    8,  62,   null);
INSERT INTO TOURIST_ITINERARY VALUES (64, 'JEBGER',    5,  63,   null);
INSERT INTO TOURIST_ITINERARY VALUES (59, 'LALEY',     4,  null, 1);
INSERT INTO TOURIST_ITINERARY VALUES (60, 'LALEY',     5,  59,   null);
INSERT INTO TOURIST_ITINERARY VALUES (53, 'MIDOUS',    10, null, 2);
INSERT INTO TOURIST_ITINERARY VALUES (54, 'MIDOUS',    11, 53,   null);
INSERT INTO TOURIST_ITINERARY VALUES (55, 'MIDOUS',    3,  54,   null);
INSERT INTO TOURIST_ITINERARY VALUES (8,  'NELWIG',    4,  null, 1);
INSERT INTO TOURIST_ITINERARY VALUES (9,  'NELWIG',    5,  8,    null);
INSERT INTO TOURIST_ITINERARY VALUES (11, 'PHOCHEL',   4,  null, 1);
INSERT INTO TOURIST_ITINERARY VALUES (12, 'PHOCHEL',   5,  11,   null);
INSERT INTO TOURIST_ITINERARY VALUES (73, 'RORIM',     10, null, 2);
INSERT INTO TOURIST_ITINERARY VALUES (74, 'RORIM',     11, 73,   null);
INSERT INTO TOURIST_ITINERARY VALUES (75, 'RORIM',     3,  74,   null);
INSERT INTO TOURIST_ITINERARY VALUES (41, 'TAMTHA',    3,  null, null);
INSERT INTO TOURIST_ITINERARY VALUES (68, 'VALNER',    4,  null, null);
INSERT INTO TOURIST_ITINERARY VALUES (69, 'VALNER',    5,  68,   null);
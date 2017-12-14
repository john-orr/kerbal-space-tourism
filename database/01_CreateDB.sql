DROP TABLE IF EXISTS TOURIST_ITINERARY;
DROP TABLE IF EXISTS MISSION;
DROP TABLE IF EXISTS VESSEL;
DROP TABLE IF EXISTS FLIGHT;
DROP TABLE IF EXISTS TOURIST;

CREATE TABLE TOURIST (
  NAME VARCHAR(20) NOT NULL,
  LOCATION VARCHAR(20) NOT NULL,
  PRIMARY KEY (NAME)
);

CREATE TABLE FLIGHT (
  ID INT NOT NULL,
  ORIGIN VARCHAR(20) NOT NULL,
  DESTINATION VARCHAR(20) NOT NULL,
  FLYBY VARCHAR(20),
  CAPACTIY INT NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE VESSEL (
  NAME VARCHAR(20) NOT NULL,
  LOCATION VARCHAR(20),
  CAPACTIY INT NOT NULL,
  PRIMARY KEY (NAME)
);

CREATE TABLE MISSION (
  ID INT NOT NULL,
  FLIGHTID INT NOT NULL,
  VESSEL VARCHAR(20) NOT NULL,
  STATUS VARCHAR(10) NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (FLIGHTID) REFERENCES FLIGHT(ID),
  FOREIGN KEY (VESSEL) REFERENCES VESSEL(NAME)
);

CREATE TABLE TOURIST_ITINERARY (
  ID INT NOT NULL,
  TOURIST VARCHAR(20) NOT NULL,
  FLIGHTID INT NOT NULL,
  PREREQUISITE INT,
  MISSIONID INT,
  FOREIGN KEY (MISSIONID) REFERENCES MISSION(ID),
  FOREIGN KEY (PREREQUISITE) REFERENCES TOURIST_ITINERARY(ID),
  FOREIGN KEY (FLIGHTID) REFERENCES FLIGHT(ID),
  FOREIGN KEY (TOURIST) REFERENCES TOURIST(NAME),
  PRIMARY KEY (ID)
);


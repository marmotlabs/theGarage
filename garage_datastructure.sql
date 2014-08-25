DROP TABLE LEVEL CASCADE;
DROP TABLE VEHICLE CASCADE;
DROP TABLE SPACE CASCADE;



CREATE TABLE LEVEL (
  LEVEL_ID bigint NOT NULL,
  STORY character varying(255) NOT NULL UNIQUE,
  CONSTRAINT LEVEL_PK PRIMARY KEY (LEVEL_ID)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE LEVEL
  OWNER TO postgres;



CREATE TABLE VEHICLE (
  VEHICLE_ID bigint NOT NULL,
  TYPE character varying(255) NOT NULL,
  LICENSE_PLATE character varying(255) NOT NULL UNIQUE,
  CONSTRAINT VEHICLE_PK PRIMARY KEY (VEHICLE_ID)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE VEHICLE
  OWNER TO postgres;



CREATE TABLE SPACE (
  SPACE_ID bigint NOT NULL,
  VEHICLE_FK bigint UNIQUE,
  LEVEL_FK bigint NOT NULL,
  POSITION bigint NOT NULL,
  CONSTRAINT SPACE_PK PRIMARY KEY (SPACE_ID),
  CONSTRAINT LEVEL_POSITION_UK UNIQUE (LEVEL_FK, POSITION),
  CONSTRAINT SPACE_VEHICLE_FK FOREIGN KEY (VEHICLE_FK)
      REFERENCES VEHICLE (VEHICLE_ID) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT SPACE_LEVEL_FK FOREIGN KEY (LEVEL_FK)
      REFERENCES LEVEL (LEVEL_ID) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE SPACE
  OWNER TO postgres;



INSERT INTO LEVEL (LEVEL_ID, STORY) VALUES (1, 'A');
INSERT INTO LEVEL (LEVEL_ID, STORY) VALUES (2, 'B');
INSERT INTO LEVEL (LEVEL_ID, STORY) VALUES (3, 'C');

INSERT INTO VEHICLE (VEHICLE_ID, TYPE, LICENSE_PLATE) VALUES (1, 'CAR', 'M1');
INSERT INTO VEHICLE (VEHICLE_ID, TYPE, LICENSE_PLATE) VALUES (2, 'CAR', 'M2');
INSERT INTO VEHICLE (VEHICLE_ID, TYPE, LICENSE_PLATE) VALUES (3, 'CAR', 'M3');
INSERT INTO VEHICLE (VEHICLE_ID, TYPE, LICENSE_PLATE) VALUES (4, 'MOTORBIKE', 'M4');

INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (1, 1, 1, 1);
INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (2, NULL, 1, 2);
INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (3, NULL, 1, 3);
INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (4, 2, 2, 1);
INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (5, NULL, 2, 2);
INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (6, 3, 3, 1);
INSERT INTO SPACE (SPACE_ID, VEHICLE_FK, LEVEL_FK,  POSITION) VALUES (7, 4, 3, 2);

-- Create sequences
CREATE SEQUENCE SQ_LEVEL CYCLE
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9999999
   CACHE 20;
ALTER SEQUENCE SQ_LEVEL
  OWNER TO postgres;

CREATE SEQUENCE SQ_SPACE CYCLE
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9999999
   CACHE 20;
ALTER SEQUENCE SQ_SPACE
  OWNER TO postgres;

CREATE SEQUENCE SQ_VEHICLE CYCLE
   INCREMENT 1
   START 1
   MINVALUE 1
   MAXVALUE 9999999
   CACHE 20;
ALTER SEQUENCE SQ_VEHICLE
  OWNER TO postgres;

 -- Count the number of free spaces in the garage 
SELECT 
   COUNT(s.SPACE_ID)
FROM
   SPACE s
WHERE
   s.VEHICLE_FK is NULL;



 -- Get space by license plate
SELECT
   s.SPACE_ID, s.VEHICLE_FK, s.LEVEL_FK, s.POSITION, v.LICENSE_PLATE
FROM
   SPACE s, VEHICLE v
WHERE
   s.VEHICLE_FK = v.VEHICLE_ID
AND
   v.LICENSE_PLATE = 'M4'; 



 -- Get the first free space
SELECT 
    s.SPACE_ID, s.VEHICLE_FK, s.LEVEL_FK, s.POSITION
FROM
    SPACE s
WHERE
    s.VEHICLE_FK is NULL
ORDER BY 
   s.SPACE_ID ASC
LIMIT 1; 




 -- Update vehicle
 INSERT INTO VEHICLE (VEHICLE_ID, TYPE, LICENSE_PLATE) VALUES (5 'CAR', 'M5');

 -- Update space - set vehicle


 -- Update space - unset vehicle


 -- Get vehicle by license plate 
SELECT 
    v.VEHICLE_ID, v.TYPE, v.LICENSE_PLATE
FROM
   VEHICLE v
WHERE
   v.LICENSE_PLATE = 'M3';


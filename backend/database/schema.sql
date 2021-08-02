BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP TABLE IF EXISTS potholes;
DROP SEQUENCE IF EXISTS seq_pothole_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE SEQUENCE seq_pothole_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TYPE pothole_status AS ENUM ('uninspected', 'under inspection', 'inspected', 'under repair', 'repaired');
CREATE TYPE pothole_dimensions AS ENUM ('0-1ft', '1-2ft', '2+ft', 'sinkhole');
CREATE TYPE pothole_severity AS ENUM ('low', 'medium', 'high', 'extreme');

CREATE TABLE potholes (
	pothole_id int DEFAULT nextval('seq_pothole_id'::regclass) NOT NULL,
	submitter_id int,
	lat varchar(50) NOT NULL,
	lon varchar(50) NOT NULL,
	addr varchar(150),
	neighborhood varchar(150),
	date_created timestamp NOT NULL,
	date_inspected timestamp,
	date_repaired timestamp,
	current_status pothole_status,
	severity pothole_severity,
	dimensions pothole_dimensions,
	notes varchar(250),
	CONSTRAINT FK_pothole_user FOREIGN KEY (submitter_id) REFERENCES users (user_id),
	CONSTRAINT PK_pothole PRIMARY KEY (pothole_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '40.20', '40.20', '123 Blah St.', 'Wilkinsburg', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '360.24', '723.94', '188 Fake St.', 'Squirrel Hill', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '1-2ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '234.58', '9347.27', '333 Unreal St.', 'Bloomfield', CURRENT_TIMESTAMP, '07/27/2021', NULL, 'under inspection', 'high', '2+ft', 'Its real big');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '2348.27', '7234.57', '666 Demon St.', 'Wilkinsburg', CURRENT_TIMESTAMP, '01/01/2021', NULL, 'inspected', 'low', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '9283.57', '1238.57', '185 Dunlap St.', 'Shadyside', CURRENT_TIMESTAMP, '03/05/2021', '05/20/2021', 'repaired', 'low', 'sinkhole', 'Bus paved over, never recovered');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '3472.57', '8347.55', '88 Colosimo Dr.', 'Bloomfield', CURRENT_TIMESTAMP, '04/20/2021', NULL, 'under repair', 'medium', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '3847.57', '2389.56', '44 Olsen Blvd.', 'Strip District', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '458.38', '848.84', '837 Street St.', 'Wilkinsburg', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '8474.27', '2389.56', '27 Maplewood Ave.', 'Bloomfield', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '2+ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '8374.56', '2480.56', '69 Yeet St.', 'Wilkinsburg', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '1-2ft', 'Destroyed axle');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '2497.56', '9292.22', '68 Car Rd.', 'Shadyside', CURRENT_TIMESTAMP, '08/02/2021', NULL, 'under inspection', 'medium', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 1), '9384.57', '2347.57', '245 Runningoutofnames Dr.', 'Squirrel Hill', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '1-2ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 2), '8374.57', '2292.25', '999 Nine Rd.', 'Wilkinsburg', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '0-1ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 2), '7727.56', '2347.22', '284 Brick Dr.', 'Shadyside', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '2+ft', 'no notes');
INSERT INTO potholes VALUES(DEFAULT, (SELECT user_id FROM users WHERE user_id = 2), '2348.56', '9929.56', '321 Bridgegif Rd.', 'Wilkinsburg', CURRENT_TIMESTAMP, NULL, NULL, 'uninspected', 'medium', '0-1ft', 'no notes');

COMMIT TRANSACTION;

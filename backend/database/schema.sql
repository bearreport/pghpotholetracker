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


COMMIT TRANSACTION;

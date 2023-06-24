-- Database: minetrack---------------------------------------------

CREATE DATABASE minetrack;

\c minetrack;

-- Table: account--------------------------------------------------

CREATE TABLE account
(
    user_id serial NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (user_id),
    CONSTRAINT account_username_key UNIQUE (username)
)
    WITH (
        OIDS=FALSE
        );
ALTER TABLE account
    OWNER TO postgres;

-- table: employee--------------------------------------------------

CREATE TABLE employee
(
    tcno numeric NOT NULL,
    fname character varying(30) NOT NULL,
    lname character varying(30) NOT NULL,
    role character varying(20),
    tagid numeric NOT NULL,
    CONSTRAINT employee_pkey PRIMARY KEY (tcno),
    CONSTRAINT employee_tagid_key UNIQUE (tagid)
)
    WITH (
        OIDS=FALSE
        );
ALTER TABLE employee
    OWNER TO postgres;

-- Table: license-----------------------------------------------------

CREATE TABLE license
(
    licensekey character varying(12) NOT NULL,
    licensevalue character varying(24) NOT NULL
)
    WITH (
        OIDS=FALSE
        );
ALTER TABLE license
    OWNER TO postgres;

-- default licensekey = 'aP2dg/Hfp8g='
-- for creating new licensevalue use DesEncrypter class

-- table: machine-----------------------------------------------------

CREATE TABLE machine
(
    mno numeric NOT NULL,
    fname character varying(30) NOT NULL,
    lname character varying(30) NOT NULL,
    role character varying(20),
    tagid numeric NOT NULL,
    CONSTRAINT machine_pkey PRIMARY KEY (mno),
    CONSTRAINT machine_tagid_key UNIQUE (tagid)
)
    WITH (
        OIDS=FALSE
        );
ALTER TABLE machine
    OWNER TO postgres;

-- Table: signal------------------------------------------------------

CREATE TABLE signal
(
    "time" timestamp without time zone,
    rid numeric,
    tid numeric,
    rssi numeric
)
    WITH (
        OIDS=FALSE
        );
ALTER TABLE signal
    OWNER TO postgres;

-- Table: signalmap----------------------------------------------------

CREATE TABLE signalmap
(
    pid numeric,
    rid numeric,
    minrssi numeric,
    maxrssi numeric
)
    WITH (
        OIDS=FALSE
        );
ALTER TABLE signalmap
    OWNER TO postgres;


--insert new account
INSERT INTO account(
    user_id, username, password)
VALUES (0, 'admin', 'admin');

-- insert license
INSERT INTO license(
    licensekey, licensevalue)
VALUES ('aP2dg/Hfp8g=', 'nNt/M9A/Hpd8yviXZ6uFYQ==');

-- insert signal positions to signalmap
INSERT INTO signalmap(
    pid, rid, minrssi, maxrssi)
VALUES
    (0, 190258, -120, -75),
    (1, 190258, -74, 0),
    (2, 190258, -120, -75),

    (3, 190281, -120, -75),
    (4, 190281, -74, 0),
    (5, 190281, -120, -75),

    (6, 190266, -120, -75),
    (7, 190266, -74, 0),
    (8, 190266, -120, -75),

    (9, 190274, -120, -75),
    (10, 190274, -74, 0),
    (11, 190274, -120, -75)
;

------------------------------------------------------------------------
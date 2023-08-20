-- Database: minetrack---------------------------------------------

-- CREATE DATABASE minetrack;

\c minetrack;

-- Table: account--------------------------------------------------

CREATE TABLE account
(
    user_id serial NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (user_id),
    CONSTRAINT account_username_key UNIQUE (username)
);

-- table: employee--------------------------------------------------

CREATE TABLE employee
(
    tcno numeric NOT NULL,
    fname character varying(30) NOT NULL,
    lname character varying(30) NOT NULL,
    role character varying(20),
    tagid character varying(20) NOT NULL,
    CONSTRAINT employee_pkey PRIMARY KEY (tcno),
    CONSTRAINT employee_tagid_key UNIQUE (tagid)
);

-- Table: license-----------------------------------------------------

CREATE TABLE license
(
    licensekey character varying(12) NOT NULL,
    licensevalue character varying(24) NOT NULL
);

-- default licensekey = 'aP2dg/Hfp8g='
-- for creating new licensevalue use DesEncrypter class

-- table: machine-----------------------------------------------------

CREATE TABLE machine
(
    mno numeric NOT NULL,
    fname character varying(30) NOT NULL,
    lname character varying(30) NOT NULL,
    role character varying(20),
    tagid character varying(20) NOT NULL,
    CONSTRAINT machine_pkey PRIMARY KEY (mno),
    CONSTRAINT machine_tagid_key UNIQUE (tagid)
);

-- Table: signal------------------------------------------------------

CREATE TABLE signal
(
    "time" timestamp without time zone,
    rid character varying(20),
    tid character varying(20),
    rssi numeric
);

-- Table: signalmap----------------------------------------------------

CREATE TABLE signalmap
(
    pid numeric,
    rid character varying(20),
    minrssi numeric,
    maxrssi numeric
);


--insert new account
INSERT INTO account(
    user_id, username, password)
VALUES (0, 'admin', 'admin');

-- insert license
INSERT INTO license(
    licensekey, licensevalue)
VALUES ('aP2dg/Hfp8g=', 'nNt/M9A/Hpcp1BXXjDZ1HQ==');

-- insert signal positions to signalmap
INSERT INTO signalmap(
    pid, rid, minrssi, maxrssi)
VALUES
    (0, '242303240010', -120, -75),
    (1, '242303240010', -74, 0),
    (2, '242303240010', -120, -75)
;

------------------------------------------------------------------------
SELECT pid, rid, minrssi, maxrssi FROM signalmap;
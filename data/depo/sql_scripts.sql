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
INSERT INTO signalmap (pid, rid, minrssi, maxrssi)
VALUES
('0', '242302200001', '-120', '-91'),
('1', '242302200001', '-90', '-81'),
('2', '242302200001', '-80', '-71'),
('3', '242302200001', '-70', '-61'),
('4', '242302200001', '-60', '-51'),
('5', '242302200001', '-50', '-41'),
('6', '242302200001', '-40', '-31'),
('7', '242302200001', '-30 ', '0'),
('8', '242302200001', '-40', '-31'),
('9', '242302200001', '-50', '-41'),
('10', '242302200001', '-60', '-51'),
('11', '242302200001', '-70', '-61'),
('12', '242302200001', '-80', '-71'),
('13', '242302200001', '-90', '-81'),
('14', '242302200001', '-120', '-91');

INSERT INTO signalmap (pid, rid, minrssi, maxrssi)
VALUES
('15', '242303240007', '-120', '-91'),
('16', '242303240007', '-90', '-81'),
('17', '242303240007', '-80', '-71'),
('18', '242303240007', '-70', '-61'),
('19', '242303240007', '-60', '-51'),
('20', '242303240007', '-50', '-41'),
('21', '242303240007', '-40', '-31'),
('22', '242303240007', '-30 ', '0'),
('23', '242303240007', '-40', '-31'),
('24', '242303240007', '-50', '-41'),
('25', '242303240007', '-60', '-51'),
('26', '242303240007', '-70', '-61'),
('27', '242303240007', '-80', '-71'),
('28', '242303240007', '-90', '-81'),
('29', '242303240007', '-120', '-91');
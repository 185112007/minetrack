# MineTrack Project

[![MineTrack Demo](https://img.youtube.com/vi/cIL427DaGfI/0.jpg)](https://www.youtube.com/watch?v=cIL427DaGfI)

Projeyi çalıştırmak için test adında bir veritabanınız olması gerekiyor,
ayrıca aşagıdaki tablolar olması gerekiyor:

```
-- Database: test

CREATE DATABASE test
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1; 

-- table: employee
    
CREATE TABLE employee(
   tcno NUMERIC NOT NULL,
   fname VARCHAR(30) NOT NULL,
   lname VARCHAR(30) NOT NULL,
   role character varying(20),
   tagid NUMERIC NOT NULL UNIQUE,
   PRIMARY KEY(tcno)
);

-- table: machine

CREATE TABLE machine(
   mno NUMERIC NOT NULL,
   fname VARCHAR(30) NOT NULL,
   lname VARCHAR(30) NOT NULL,
   role character varying(20),
   tagid NUMERIC NOT NULL UNIQUE,
   PRIMARY KEY(mno)
);

-- Table: signal

CREATE TABLE signal
(
  "time" timestamp without time zone,
  rid numeric,
  tid numeric,
  rssi numeric
)

-- Table: signalmap

CREATE TABLE signalmap
(
  pid numeric,
  rid numeric,
  minrssi numeric,
  maxrssi numeric
)

-- Table: account

CREATE TABLE account(
 user_id serial PRIMARY KEY,
 username VARCHAR (50) UNIQUE NOT NULL,
 password VARCHAR (50) NOT NULL
);

-- Table: license

-- default licensekey = 'aP2dg/Hfp8g='
-- for creating new licensevalue use DesEncrypter class

CREATE TABLE license
(
  licensekey character varying(12) NOT NULL,
  licensevalue character varying(24) NOT NULL
);
```

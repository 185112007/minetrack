--
-- PostgreSQL database dump
--

-- Dumped from database version 12.18 (Debian 12.18-1.pgdg110+2)
-- Dumped by pg_dump version 12.18 (Debian 12.18-1.pgdg110+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: mine
--

CREATE TABLE public.account (
    user_id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE public.account OWNER TO postgres;

--
-- Name: account_user_id_seq; Type: SEQUENCE; Schema: public; Owner: mine
--

CREATE SEQUENCE public.account_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_user_id_seq OWNER TO postgres;

--
-- Name: account_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: mine
--

ALTER SEQUENCE public.account_user_id_seq OWNED BY public.account.user_id;


--
-- Name: employee; Type: TABLE; Schema: public; Owner: mine
--

CREATE TABLE public.employee (
    tcno numeric NOT NULL,
    fname character varying(30) NOT NULL,
    lname character varying(30) NOT NULL,
    role character varying(20),
    tagid character varying(20) NOT NULL
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- Name: license; Type: TABLE; Schema: public; Owner: mine
--

CREATE TABLE public.license (
    licensekey character varying(12) NOT NULL,
    licensevalue character varying(24) NOT NULL
);


ALTER TABLE public.license OWNER TO postgres;

--
-- Name: machine; Type: TABLE; Schema: public; Owner: mine
--

CREATE TABLE public.machine (
    mno numeric NOT NULL,
    fname character varying(30) NOT NULL,
    lname character varying(30) NOT NULL,
    role character varying(20),
    tagid character varying(20) NOT NULL
);


ALTER TABLE public.machine OWNER TO postgres;

--
-- Name: signal; Type: TABLE; Schema: public; Owner: mine
--

CREATE TABLE public.signal (
    "time" timestamp without time zone,
    rid character varying(20),
    tid character varying(20),
    rssi numeric
);


ALTER TABLE public.signal OWNER TO postgres;

--
-- Name: signalmap; Type: TABLE; Schema: public; Owner: mine
--

CREATE TABLE public.signalmap (
    pid numeric,
    rid character varying(20),
    minrssi numeric,
    maxrssi numeric
);


ALTER TABLE public.signalmap OWNER TO postgres;

--
-- Name: account user_id; Type: DEFAULT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.account ALTER COLUMN user_id SET DEFAULT nextval('public.account_user_id_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: mine
--

COPY public.account (user_id, username, password) FROM stdin;
0	admin	admin
\.


--
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: mine
--

COPY public.employee (tcno, fname, lname, role, tagid) FROM stdin;
12345	giristeki	etiket	etiket	25125200C1
23456	galeri 1	deki tag	etiket	2512520014
34567	galeri 2	deki tag	etiket	2512520068
\.


--
-- Data for Name: license; Type: TABLE DATA; Schema: public; Owner: mine
--

COPY public.license (licensekey, licensevalue) FROM stdin;
aP2dg/Hfp8g=	VzORdlI9mT3q8l1rv/me2w==
\.


--
-- Data for Name: machine; Type: TABLE DATA; Schema: public; Owner: mine
--

COPY public.machine (mno, fname, lname, role, tagid) FROM stdin;
\.


--
-- Data for Name: signal; Type: TABLE DATA; Schema: public; Owner: mine
--

COPY public.signal ("time", rid, tid, rssi) FROM stdin;
\.


--
-- Data for Name: signalmap; Type: TABLE DATA; Schema: public; Owner: mine
--

COPY public.signalmap (pid, rid, minrssi, maxrssi) FROM stdin;
0	242312220022	-120	-91
1	242312220022	-90	-81
2	242312220022	-80	-71
3	242312220022	-70	-61
4	242312220022	-60	-51
5	242312220022	-50	-41
6	242312220022	-40	-31
7	242312220022	-30	0
8	242312220022	-40	-31
9	242312220022	-50	-41
10	242312220022	-60	-51
11	242312220022	-70	-61
12	242312220022	-80	-71
13	242312220022	-90	-81
14	242312220022	-120	-91
15	242312220024	-120	-91
16	242312220024	-90	-81
17	242312220024	-80	-71
18	242312220024	-70	-61
19	242312220024	-60	-51
20	242312220024	-50	-41
21	242312220024	-40	-31
22	242312220024	-30	0
23	242312220024	-40	-31
24	242312220024	-50	-41
25	242312220024	-60	-51
26	242312220024	-70	-61
27	242312220024	-80	-71
28	242312220024	-90	-81
29	242312220024	-120	-91
30	242312220025	-120	-91
31	242312220025	-90	-81
32	242312220025	-80	-71
33	242312220025	-70	-61
34	242312220025	-60	-51
35	242312220025	-50	-41
36	242312220025	-40	-31
37	242312220025	-30	0
38	242312220025	-40	-31
39	242312220025	-50	-41
40	242312220025	-60	-51
41	242312220025	-70	-61
42	242312220025	-80	-71
43	242312220025	-90	-81
44	242312220025	-120	-91
45	242312220028	-120	-91
46	242312220028	-90	-81
47	242312220028	-80	-71
48	242312220028	-70	-61
49	242312220028	-60	-51
50	242312220028	-50	-41
51	242312220028	-40	-31
52	242312220028	-30	0
53	242312220028	-40	-31
54	242312220028	-50	-41
55	242312220028	-60	-51
56	242312220028	-70	-61
57	242312220028	-80	-71
58	242312220028	-90	-81
59	242312220028	-120	-91
60	242312220044	-120	-91
61	242312220044	-90	-81
62	242312220044	-80	-71
63	242312220044	-70	-61
64	242312220044	-60	-51
65	242312220044	-50	-41
66	242312220044	-40	-31
67	242312220044	-30	0
68	242312220044	-40	-31
69	242312220044	-50	-41
70	242312220044	-60	-51
71	242312220044	-70	-61
72	242312220044	-80	-71
73	242312220044	-90	-81
74	242312220044	-120	-91
75	242312220050	-120	-91
76	242312220050	-90	-81
77	242312220050	-80	-71
78	242312220050	-70	-61
79	242312220050	-60	-51
80	242312220050	-50	-41
81	242312220050	-40	-31
82	242312220050	-30	0
83	242312220050	-40	-31
84	242312220050	-50	-41
85	242312220050	-60	-51
86	242312220050	-70	-61
87	242312220050	-80	-71
88	242312220050	-90	-81
89	242312220050	-120	-91
90	242312220055	-120	-91
91	242312220055	-90	-81
92	242312220055	-80	-71
93	242312220055	-70	-61
94	242312220055	-60	-51
95	242312220055	-50	-41
96	242312220055	-40	-31
97	242312220055	-30	0
98	242312220055	-40	-31
99	242312220055	-50	-41
100	242312220055	-60	-51
101	242312220055	-70	-61
102	242312220055	-80	-71
103	242312220055	-90	-81
104	242312220055	-120	-91
105	242312220056	-120	-91
106	242312220056	-90	-81
107	242312220056	-80	-71
108	242312220056	-70	-61
109	242312220056	-60	-51
110	242312220056	-50	-41
111	242312220056	-40	-31
112	242312220056	-30	0
113	242312220056	-40	-31
114	242312220056	-50	-41
115	242312220056	-60	-51
116	242312220056	-70	-61
117	242312220056	-80	-71
118	242312220056	-90	-81
119	242312220056	-120	-91
120	242312220057	-120	-91
121	242312220057	-90	-81
122	242312220057	-80	-71
123	242312220057	-70	-61
124	242312220057	-60	-51
125	242312220057	-50	-41
126	242312220057	-40	-31
127	242312220057	-30	0
128	242312220057	-40	-31
129	242312220057	-50	-41
130	242312220057	-60	-51
131	242312220057	-70	-61
132	242312220057	-80	-71
133	242312220057	-90	-81
134	242312220057	-120	-91
135	242312220058	-120	-91
136	242312220058	-90	-81
137	242312220058	-80	-71
138	242312220058	-70	-61
139	242312220058	-60	-51
140	242312220058	-50	-41
141	242312220058	-40	-31
142	242312220058	-30	0
143	242312220058	-40	-31
144	242312220058	-50	-41
145	242312220058	-60	-51
146	242312220058	-70	-61
147	242312220058	-80	-71
148	242312220058	-90	-81
149	242312220058	-120	-91
\.


--
-- Name: account_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: mine
--

SELECT pg_catalog.setval('public.account_user_id_seq', 1, false);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (user_id);


--
-- Name: account account_username_key; Type: CONSTRAINT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_username_key UNIQUE (username);


--
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (tcno);


--
-- Name: employee employee_tagid_key; Type: CONSTRAINT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_tagid_key UNIQUE (tagid);


--
-- Name: machine machine_pkey; Type: CONSTRAINT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.machine
    ADD CONSTRAINT machine_pkey PRIMARY KEY (mno);


--
-- Name: machine machine_tagid_key; Type: CONSTRAINT; Schema: public; Owner: mine
--

ALTER TABLE ONLY public.machine
    ADD CONSTRAINT machine_tagid_key UNIQUE (tagid);


--
-- PostgreSQL database dump complete
--


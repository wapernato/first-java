--
-- PostgreSQL database dump
--

\restrict StFC8e7Lx2wHeAik4TsOluWo4bhw7uQDGCnkEc19tPcRVcEzpo2QhGt3ZVvQNDL

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-02-10 19:08:49 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 221 (class 1259 OID 16420)
-- Name: laptop; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.laptop (
    code integer,
    model character varying(50),
    speed smallint,
    ram smallint,
    hd real,
    price numeric(10,2),
    screen numeric(4,1)
);


ALTER TABLE public.laptop OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16417)
-- Name: pc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pc (
    code integer,
    model character varying(50),
    speed smallint,
    ram smallint,
    hd real,
    cd character varying(10),
    price numeric(10,2)
);


ALTER TABLE public.pc OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16423)
-- Name: printer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.printer (
    code integer,
    model character varying(50),
    color character(1),
    type character varying(10),
    price numeric(10,2)
);


ALTER TABLE public.printer OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16406)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    maker character varying(10) NOT NULL,
    model character varying(50) NOT NULL,
    product_type character varying(50) NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 3835 (class 0 OID 16420)
-- Dependencies: 221
-- Data for Name: laptop; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.laptop (code, model, speed, ram, hd, price, screen) FROM stdin;
1	lp1	1000	4000	1080	100000.00	22.0
2	lp2	2000	6000	2048	120000.00	23.0
3	lp3	3000	8000	4096	180000.00	24.0
\.


--
-- TOC entry 3834 (class 0 OID 16417)
-- Dependencies: 220
-- Data for Name: pc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pc (code, model, speed, ram, hd, cd, price) FROM stdin;
1	hp1	1000	25	1000	4x	100000.00
2	hp2	2000	25	2000	6x	150000.00
3	hp3	3000	25	3000	8x	200000.00
1	lp1	1000	22	1000	2x	100000.00
2	lp2	2000	23	2000	4x	120000.00
3	lp3	3000	25	3000	6x	180000.00
1	lp1	1000	22	1000	2x	100000.00
2	lp2	2000	23	2000	4x	120000.00
3	lp3	3000	25	3000	6x	180000.00
\.


--
-- TOC entry 3836 (class 0 OID 16423)
-- Dependencies: 222
-- Data for Name: printer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.printer (code, model, color, type, price) FROM stdin;
1	pr1	y	Jet	10000.00
2	pr2	y	Matrix	12000.00
3	pr3	n	Laser	18000.00
\.


--
-- TOC entry 3833 (class 0 OID 16406)
-- Dependencies: 219
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (maker, model, product_type) FROM stdin;
intel	hp1	PC
intel	hp2	PC
intel	hp3	PC
epson	pr1	Printer
epson	pr2	Printer
epson	pr3	Printer
hyperX	lp1	Laptop
hyperX	lp2	Laptop
hyperX	lp3	Laptop
\.


--
-- TOC entry 3682 (class 2606 OID 16413)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (model);


--
-- TOC entry 3684 (class 2606 OID 16441)
-- Name: laptop laptop_model_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laptop
    ADD CONSTRAINT laptop_model_fk FOREIGN KEY (model) REFERENCES public.product(model);


--
-- TOC entry 3683 (class 2606 OID 16426)
-- Name: pc pc_model_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pc
    ADD CONSTRAINT pc_model_fk FOREIGN KEY (model) REFERENCES public.product(model);


--
-- TOC entry 3685 (class 2606 OID 16436)
-- Name: printer printer_model_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.printer
    ADD CONSTRAINT printer_model_fk FOREIGN KEY (model) REFERENCES public.product(model);


-- Completed on 2026-02-10 19:08:49 MSK

--
-- PostgreSQL database dump complete
--

\unrestrict StFC8e7Lx2wHeAik4TsOluWo4bhw7uQDGCnkEc19tPcRVcEzpo2QhGt3ZVvQNDL


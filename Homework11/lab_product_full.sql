--
-- PostgreSQL database dump
--

\restrict VZCu2bgWrFXMaiQMa6u49AJfTRhsPqxunN3yanY1UwscOn237KflXJvG3OVeYi8

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

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
-- Name: laptop; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.laptop (
    code integer,
    model character varying(50),
    speed smallint,
    ram smallint,
    hd real,
    cd numeric(10,2),
    price smallint
);


ALTER TABLE public.laptop OWNER TO postgres;

--
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
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    maker character varying(10) NOT NULL,
    model character varying(50) NOT NULL,
    product_type character varying(50) NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Data for Name: laptop; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.laptop (code, model, speed, ram, hd, cd, price) FROM stdin;
\.


--
-- Data for Name: pc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pc (code, model, speed, ram, hd, cd, price) FROM stdin;
\.


--
-- Data for Name: printer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.printer (code, model, color, type, price) FROM stdin;
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (maker, model, product_type) FROM stdin;
\.


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (model);


--
-- Name: laptop laptop_model_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.laptop
    ADD CONSTRAINT laptop_model_fk FOREIGN KEY (model) REFERENCES public.product(model);


--
-- Name: pc pc_model_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pc
    ADD CONSTRAINT pc_model_fk FOREIGN KEY (model) REFERENCES public.product(model);


--
-- Name: printer printer_model_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.printer
    ADD CONSTRAINT printer_model_fk FOREIGN KEY (model) REFERENCES public.product(model);


--
-- PostgreSQL database dump complete
--

\unrestrict VZCu2bgWrFXMaiQMa6u49AJfTRhsPqxunN3yanY1UwscOn237KflXJvG3OVeYi8


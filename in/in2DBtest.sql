--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.4
-- Dumped by pg_dump version 9.2.4
-- Started on 2013-06-20 23:15:16 ART

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 173 (class 3079 OID 11995)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2210 (class 0 OID 0)
-- Dependencies: 173
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 170 (class 1259 OID 24593)
-- Name: fact; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fact (
	emission numeric,
	bademission numeric,
 	cloud integer,
	factory integer,
	beg integer,
	endD integer,
	sustanciaDim integer
);


ALTER TABLE public.fact OWNER TO postgres;



CREATE TABLE factory (
    latitud bigint,
    longitud integer,
    staffqty numeric,
    employer_name varchar,
    city_name varchar,
    fact_anual bigint,
    population bigint,
    country_name varchar,
    gubernant varchar,
    "TipoIdRubro" varchar,
    "TipoDesc" varchar
);


ALTER TABLE public.factory OWNER TO postgres;


CREATE TABLE filtration (
);


ALTER TABLE public.filtration OWNER TO postgres;



CREATE TABLE substance (
    denominacion varchar
);


ALTER TABLE public.substance OWNER TO postgres;



CREATE TABLE temporal (
    dia varchar,
    mes numeric,
    year numeric,
    es_bisiesto numeric,
    name varchar
);


ALTER TABLE public.temporal OWNER TO postgres;


REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;




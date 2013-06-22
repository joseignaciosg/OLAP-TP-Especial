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




CREATE TABLE factory (
    latitud numeric PRIMARY KEY,
    longitud numeric,
    staffqty numeric,
    company varchar,
    city_name varchar,
    fact_anual numeric,
    population numeric,
    country_name varchar,
    the_geom integer,
    "TipoIdRubro" numeric,
    "TipoDesc" varchar
);


ALTER TABLE public.factory OWNER TO postgres;


CREATE TABLE filtration (
);


ALTER TABLE public.filtration OWNER TO postgres;



CREATE TABLE substance (
    denominacion varchar PRIMARY KEY
);


ALTER TABLE public.substance OWNER TO postgres;



CREATE TABLE temporal (
    dia Timestamp PRIMARY KEY,
    mes numeric,
    year numeric,
    es_bisiesto numeric,
    name varchar
);


ALTER TABLE public.temporal OWNER TO postgres;


CREATE TABLE fact (
	emission numeric,
	bademission numeric,
 	cloud integer,
	factory integer REFERENCES factory(latitud),
	beg timestamp REFERENCES temporal(dia),
	endD timestamp REFERENCES temporal(dia),
	sustanciaDim varchar REFERENCES substance(denominacion)
);


ALTER TABLE public.fact OWNER TO postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;




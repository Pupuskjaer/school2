--liquibase formatted sql

--changeset trkhasanov:1

create table students (
id serial,
name text,
age integer
)
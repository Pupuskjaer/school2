create table driver (
id smallserial primary key,
name text not null,
age smallint,
driver_license boolean,
id_car references car(id);
create table car(
id smallserial primary key,
brand text not null,
model text,
price smallint not null);
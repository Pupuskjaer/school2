alter table student
add constraint age_constraint check( age > 16 ),
alter column name set not null,
add constraint name_unique unique (name),
alter column age set DEFAULT 20:

alter table faculty
add constraint name_color_unique unique(name,color);
create table users
(
    id            integer generated by default as identity
        constraint registeredperson_pkey
            primary key,
    name          varchar(30)  not null,
    password      varchar(500) not null,
    year_of_birth integer      not null
);



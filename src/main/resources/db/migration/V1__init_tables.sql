create schema if not exists movie;

create table if not exists movie.director (
    id          bigserial primary key,
    fio         varchar(100) not null
);


create table if not exists movie.movie (
    id          bigserial primary key,
    title       varchar(100) not null,
    year        int not null,
    director    bigint references movie.director(id),
    length      time not null,
    rating      int not null
);
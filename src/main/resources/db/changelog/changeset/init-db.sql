--changeset RuslanYuneev:init
--comment Init migration


create table bank_accounts (
    id bigserial primary key,
    details varchar(16) not null unique,
    amount numeric not null,
    increasing_limit numeric not null
);

create table users (
    id bigserial primary key,
    username varchar(30) not null unique,
    password varchar(80) not null,

    full_name varchar(100) not null,
    date_of_birth date not null,
    bank_account bigint references bank_accounts(id)
);

create table roles (
    id serial primary key,
    name varchar(50) not null unique
);

create table users_roles (
    user_id bigint not null references users(id),
    role_id bigint not null references roles(id),
    primary key (user_id, role_id)
);

create table phones (
    id bigserial primary key,
    number varchar(30) not null unique,
    user_id bigint references users(id)
);

create table emails(
    id bigserial primary key,
    email varchar(30) not null unique,
    user_id bigint references users(id)
);
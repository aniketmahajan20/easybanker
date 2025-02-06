-- A place where you can set up your database before running your application
create table mystudent(
	username VARCHAR(50) not null primary key,
	password VARCHAR(50) not null,
	enabled boolean not null
);

-- The Authorities table contains the authorizations provided to each user
-- One user can have many authorizations. Therefore it has one-many mapping 
-- with the user table 
create table authorities (
	username VARCHAR(50) not null,
	authority VARCHAR(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
-- Creating unique index in the authorities table for fast lookup
create unique index ix_auth_username on authorities (username,authority);
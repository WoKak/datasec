DROP TABLE users
IF EXISTS;

DROP TABLE questions
IF EXISTS;

DROP TABLE snippets
IF EXISTS;

CREATE TABLE users (

    login character(50),
    password character(64)
);

CREATE TABLE questions (

    login character(50),
    question character(50),
    answer character(50)
);

CREATE TABLE snippets (

    code character(50)
);
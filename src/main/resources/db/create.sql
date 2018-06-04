SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS posts (
    id int PRIMARY KEY auto_increment,
    content VARCHAR,
    published boolean,
);
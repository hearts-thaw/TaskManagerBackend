DROP TABLE IF EXISTS taskusers;
DROP TABLE IF EXISTS tasks;


CREATE TABLE taskusers
(
    id       IDENTITY,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    role     TEXT
);

CREATE TABLE tasks
(
    id        IDENTITY,
    text      TEXT      NOT NULL,
    dateTime  TIMESTAMP NOT NULL,
    completed BOOLEAN   NOT NULL,
    userid    BIGINT,

    FOREIGN KEY (userid) REFERENCES taskusers (id)
);

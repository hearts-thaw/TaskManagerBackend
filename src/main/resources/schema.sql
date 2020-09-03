DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks
(
    id        IDENTITY,
    text      TEXT      NOT NULL,
    dateTime  TIMESTAMP NOT NULL,
    completed BOOLEAN   NOT NULL
);

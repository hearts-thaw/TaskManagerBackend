INSERT INTO taskusers (username, password, role)
values ('foo', '$2a$10$I4kX6aFhDm7zSdwIflB4te1wrpEZ9Euzj2ZJS/ry0JGnUaScfoWq2', 'user');

INSERT INTO taskusers (username, password, role)
values ('bar', '$2a$10$I4kX6aFhDm7zSdwIflB4te1wrpEZ9Euzj2ZJS/ry0JGnUaScfoWq2', 'admin');

INSERT INTO tasks (text, dateTime, completed, userid)
values ('First task', CURRENT_TIMESTAMP(), FALSE, 1),
       ('Second task', CURRENT_TIMESTAMP(), TRUE, 2);


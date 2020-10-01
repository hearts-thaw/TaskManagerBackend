INSERT INTO groups (groupname)
VALUES ('foo');

INSERT INTO taskusers (username, password, role)
VALUES ('foo', '$2a$10$I4kX6aFhDm7zSdwIflB4te1wrpEZ9Euzj2ZJS/ry0JGnUaScfoWq2', 'user');

INSERT INTO groups_taskusers (group_id, taskuser_id)
VALUES (1, 1);

INSERT INTO froth (frothname)
VALUES ('froth');

INSERT INTO tasks (tasktext, completed, datetime, onfire, froth_id)
VALUES ('First task', '1', current_timestamp, '0', 1),
       ('Second task', '1', current_timestamp, '0', 1);

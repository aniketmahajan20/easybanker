INSERT INTO mystudent (student_name, password, active, id, roles)
    values ('student',
        'password',
        true,
        1,
        'ROLE_STUDENT')

INSERT INTO mystudent (student_name, password, active, id, roles)
    values ('teacher',
        'password',
        true,
        2,
        'ROLE_TEACHER')

INSERT INTO authorities (username, authority)
    values ('student', 'ROLE_STUDENT')

INSERT INTO authorities (username, authority)
    values ('teacher', 'ROLE_TEACHER')
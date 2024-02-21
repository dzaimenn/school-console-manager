INSERT INTO groups (group_name)
VALUES ('A'),
       ('B');

INSERT INTO students (group_id, first_name, last_name)
VALUES (1, 'Alex', 'Williams'),
       (1, 'Eva', 'Miller'),
       (2, 'Leon', 'Kennedy');

INSERT INTO courses (course_name, course_description)
VALUES ('Java Basics', 'Introduction to Java programming'),
       ('Databases', 'Introduction to databases');

INSERT INTO student_courses (student_id, course_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 1),
       (3, 2);
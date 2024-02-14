INSERT INTO groups (group_id, group_name)
VALUES (1, 'AA'),
       (2, 'BB');

INSERT INTO students (student_id, group_id, first_name, last_name)
VALUES (1, 1, 'Alex', 'Williams'),
       (2, 1, 'Eva', 'Miller'),
       (3, 2, 'Leon', 'Kennedy');

INSERT INTO courses (course_id, course_name, course_description)
VALUES (1, 'Java Basics', 'Introduction to Java programming'),
       (2, 'Databases', 'Introduction to databases');

INSERT INTO student_courses (student_id, course_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 1);
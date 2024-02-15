CREATE TABLE IF NOT EXISTS groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(128),
    course_description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student_courses
(
    student_id INT REFERENCES students (student_id),
    course_id  INT REFERENCES courses (course_id),
    CONSTRAINT student_courses_pk PRIMARY KEY (student_id, course_id)
);

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
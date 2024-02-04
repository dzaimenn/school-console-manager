package foxminded.dzaimenko.schoolspring.util;


import foxminded.dzaimenko.schoolspring.util.SchoolData;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class DatabaseFiller {
    private final Random random;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_GROUPS = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String SQL_INSERT_STUDENTS = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String SQL_INSERT_COURSES = "INSERT INTO courses (course_name, course_description) VALUES (?,?)";
    private static final String SQL_INSERT_STUDENT_COURSES = "INSERT INTO student_courses (student_id, course_id) VALUES (?,?)";

    public DatabaseFiller(Random random, JdbcTemplate jdbcTemplate) {
        this.random = random;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void fillDataBase() {
        groupsTableFill();
        studentsTableFill();
        coursesTableFill();
        studentsCoursesTableFill();
        System.out.println("Database filled successfully");
    }

    private void groupsTableFill() {

        for (int i = 0; i < 10; i++) {
            String groupName = SchoolData.groupsNames[i];

            jdbcTemplate.update(SQL_INSERT_GROUPS, groupName);
        }
    }

    private void studentsTableFill() {

        for (int i = 1; i <= 200; i++) {

            int groupId = random.nextInt(10) + 1;
            String firstName = SchoolData.firstNamesArray[random.nextInt(20)];
            String lastName = SchoolData.lastNamesArray[random.nextInt(20)];

            jdbcTemplate.update(SQL_INSERT_STUDENTS, groupId, firstName, lastName);
        }
    }

    private void coursesTableFill() {

        for (int i = 0; i < 10; i++) {

            String courseName = SchoolData.coursesNames[i];
            String courseDescription = SchoolData.coursesDescriptions[i];

            jdbcTemplate.update(SQL_INSERT_COURSES, courseName, courseDescription);
        }
    }

    private void studentsCoursesTableFill() {

        for (int studentId = 1; studentId <= 200; studentId++) {

            Set<Integer> assignedCourses = new HashSet<>();
            int numberOfCourses = random.nextInt(3) + 1;

            while (assignedCourses.size() < numberOfCourses) {
                int courseId = random.nextInt(10) + 1;

                if (assignedCourses.add(courseId)) {
                    jdbcTemplate.update(SQL_INSERT_STUDENT_COURSES, studentId, courseId);
                }
            }
        }
    }

}
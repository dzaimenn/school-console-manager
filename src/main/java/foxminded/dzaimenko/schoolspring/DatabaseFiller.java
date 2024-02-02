package foxminded.dzaimenko.schoolspring;


import foxminded.dzaimenko.schoolspring.util.SchoolData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class DatabaseFiller {

    private static final String FAILED_TO_RETRIEVE_KEYS_ERROR = "Failed to retrieve generated keys.";
    Random random = new Random();
    private final JdbcTemplate jdbcTemplate;

    public DatabaseFiller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void fillDataBase() {
        groupsTableFill();
        studentsTableFill();
        coursesTableFill();
        studentsCoursesTableFill();
    }

    private void groupsTableFill() {

        String insertGroupsQuery = "INSERT INTO groups (group_name) VALUES (?)";
        for (int i = 0; i < 10; i++) {
            String groupName = SchoolData.groupsNames[i];
            jdbcTemplate.update(insertGroupsQuery, groupName);
        }
    }

    private void studentsTableFill() {

        String insertStudentsQuery = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";

        for (int i = 1; i <= 200; i++) {
            int groupId = random.nextInt(10) + 1;
            String firstName = SchoolData.firstNamesArray[random.nextInt(20)];
            String lastName = SchoolData.lastNamesArray[random.nextInt(20)];
            jdbcTemplate.update(insertStudentsQuery, groupId, firstName, lastName);
        }
    }

    private void coursesTableFill() {
        String insertCoursesQuery = "INSERT INTO courses (course_name, course_description) VALUES (?,?)";
        for (int i = 0; i < 10; i++) {
            String courseName = SchoolData.coursesNames[i];
            String courseDescription = SchoolData.coursesDescriptions[i];
            jdbcTemplate.update(insertCoursesQuery, courseName, courseDescription);
        }
    }

    private void studentsCoursesTableFill() {
        String insertStudentCoursesQuery = "INSERT INTO student_courses (student_id, course_id) VALUES (?,?)";

        for (int studentId = 1; studentId <= 200; studentId++) {

            Set<Integer> assignedCourses = new HashSet<>();
            int numberOfCourses = random.nextInt(3) + 1;

            while (assignedCourses.size() < numberOfCourses) {
                int courseId = random.nextInt(10) + 1;

                if (assignedCourses.add(courseId)) {
                    jdbcTemplate.update(insertStudentCoursesQuery, studentId, courseId);
                }
            }
        }
    }

}
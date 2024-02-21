package foxminded.dzaimenko.schoolspring.util;

import foxminded.dzaimenko.schoolspring.model.Course;
import foxminded.dzaimenko.schoolspring.model.Group;
import foxminded.dzaimenko.schoolspring.model.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class DatabaseFiller {
    private final Random random = new Random();
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT_GROUPS = "INSERT INTO groups (group_name) VALUES (?)";
    private final String SQL_INSERT_STUDENTS = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private final String SQL_INSERT_COURSES = "INSERT INTO courses (course_name, course_description) VALUES (?,?)";
    private final String SQL_INSERT_STUDENT_COURSES = "INSERT INTO student_courses (student_id, course_id) VALUES (?,?)";

    private final String SQL_GET_GROUP_ID = "SELECT group_id FROM groups WHERE group_name = ?";
    private final String SQL_GET_COURSE_ID = "SELECT course_id FROM courses WHERE course_name = ?";
    private final String SQL_GET_STUDENT_ID = "SELECT student_id FROM students WHERE first_name = ? AND last_name = ?";

    public final List<Group> groups = new ArrayList<>();
    public final List<Course> courses = new ArrayList<>();

    public final List<Student> students = new ArrayList<>();

    public DatabaseFiller(JdbcTemplate jdbcTemplate) {
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
            Group group = new Group();
            group.setName(groupName);


            jdbcTemplate.update(SQL_INSERT_GROUPS, groupName);

            Integer id = jdbcTemplate.queryForObject(SQL_GET_GROUP_ID, Integer.class, groupName);
            group.setId(id);

            groups.add(group);
        }
    }

    private Set<String> generateUniqueStudentsSet() {
        Set<String> uniqueStudents = new HashSet<>();

        while (uniqueStudents.size() < 200) {
            String firstName = SchoolData.firstNamesArray[random.nextInt(20)];
            String lastName = SchoolData.lastNamesArray[random.nextInt(20)];

            String uniqueKey = firstName + " " + lastName;

            uniqueStudents.add(uniqueKey);
        }

        return uniqueStudents;
    }

    private void studentsTableFill() {
        Set<String> uniqueStudents = generateUniqueStudentsSet();

        for (String uniqueKey : uniqueStudents) {
            Integer groupId = groups.get(random.nextInt(10)).getId();
            String[] nameParts = uniqueKey.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            Student student = new Student();
            student.setStudentId(groupId);
            student.setFirstName(firstName);
            student.setLastName(lastName);

            jdbcTemplate.update(SQL_INSERT_STUDENTS, groupId, firstName, lastName);

            Integer id = jdbcTemplate.queryForObject(SQL_GET_STUDENT_ID, Integer.class, firstName, lastName);
            student.setGroupId(id);

            students.add(student);
        }
    }

    private void coursesTableFill() {

        for (int i = 0; i < 10; i++) {
            String courseName = SchoolData.coursesNames[i];
            String courseDescription = SchoolData.coursesDescriptions[i];

            Course course = new Course();
            course.setName(courseName);
            course.setDescription(courseDescription);

            jdbcTemplate.update(SQL_INSERT_COURSES, courseName, courseDescription);

            Integer id = jdbcTemplate.queryForObject(SQL_GET_COURSE_ID, Integer.class, courseName);
            course.setId(id);

            courses.add(course);
        }

    }

    private void studentsCoursesTableFill() {

        for (int studentId = 1; studentId <= 200; studentId++) {

            Set<Integer> assignedCourses = new HashSet<>();
            int numberOfCourses = random.nextInt(3) + 1;

            while (assignedCourses.size() < numberOfCourses) {
                int courseId = courses.get(random.nextInt(10)).getId();

                if (assignedCourses.add(courseId)) {
                    jdbcTemplate.update(SQL_INSERT_STUDENT_COURSES, studentId, courseId);
                }
            }
        }
    }

}
package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"classpath:/sql/drop_test_tables.sql",
                "classpath:/db/migration/V1__Create_Tables.sql",
                "classpath:/sql/insert_test_data.sql"},

        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@ComponentScan(basePackages = "foxminded.dzaimenko.schoolspring.dao")
class JdbcStudentDaoTest {

    @Autowired
    private StudentDao dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Student> prepareExpectedStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(Student.builder().studentId(1).groupId(1).firstName("Alex").lastName("Williams").build());
        expectedStudents.add(Student.builder().studentId(2).groupId(1).firstName("Eva").lastName("Miller").build());
        expectedStudents.add(Student.builder().studentId(3).groupId(2).firstName("Leon").lastName("Kennedy").build());

        return expectedStudents;
    }

    @Test
    void testGetAll() {
        List<Student> expected = prepareExpectedStudents();
        List<Student> actual = dao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {
        Student student = Student.builder()
                .firstName("Alice")
                .lastName("Smith")
                .build();

        dao.create(student);

        int expected = 1;
        int actualFirstName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "students",
                "first_name = 'Alice'");

        int actualLastName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "students",
                "last_name = 'Smith'");

        assertEquals(expected, actualFirstName);
        assertEquals(expected, actualLastName);
    }

    @Test
    void testUpdate() {
        Student student = Student.builder()
                .studentId(1)
                .firstName("Alice")
                .lastName("Smith")
                .build();

        dao.update(student);

        int expected = 1;
        int actualFirstName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "students",
                "student_id = 1 AND first_name = 'Alice'");

        int actualLastName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "students",
                "student_id = 1 AND last_name = 'Smith'");

        assertEquals(expected, actualFirstName);
        assertEquals(expected, actualLastName);
    }

    @Test
    void testDelete() {
        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students") - 1;

        dao.deleteById(1);
        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");

        assertEquals(expected, actual);
    }

    @Test
    void testFindStudentsByCourseName() {
        String courseName = "Java Basics";
        List<Student> students = dao.findByCourseName(courseName);

        int expected = 2;
        int actual = students.size();

        assertEquals(expected, actual);
    }

    @Test
    void testAddStudentToCourse() {
        int studentId = 2;
        int courseId = 1;

        dao.addToCourse(studentId, courseId);
        List<Student> students = dao.findByCourseName("Java Basics");

        int expected = 3;
        int actual = students.size();

        assertEquals(expected, actual);
    }

    @Test
    void testRemoveStudentFromCourse() {
        int studentIdToRemoveFromCourse = 1;
        int courseId = 1;

        dao.removeFromCourse(studentIdToRemoveFromCourse, courseId);
        List<Student> students = dao.findByCourseName("Java Basics");

        int expected = 1;
        int actual = students.size();

        assertEquals(expected, actual);
    }

    @Test
    void testGetNumberOfStudents() {
        int expected = 3;
        int actual = dao.getTotalNumber();

        assertEquals(expected, actual);
    }

}
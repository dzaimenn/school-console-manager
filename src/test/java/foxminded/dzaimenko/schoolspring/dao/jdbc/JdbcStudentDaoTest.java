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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Test
    void testGetAll() {
        List<Student> expected = new ArrayList<>();
        expected.add(Student.builder().id(1).groupId(1).firstName("Alex").lastName("Williams").build());
        expected.add(Student.builder().id(2).groupId(1).firstName("Eva").lastName("Miller").build());
        expected.add(Student.builder().id(3).groupId(2).firstName("Leon").lastName("Kennedy").build());

        List<Student> actual = dao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {
        Student student = Student.builder()
                .groupId(1)
                .firstName("NewFirstName")
                .lastName("NewLastName")
                .build();

        dao.create(student);

        int expected = 1;
        int actual = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "students",
                "group_id = '1' AND first_name = 'NewFirstName' AND last_name = 'NewLastName'");

        assertEquals(expected, actual);
    }

    @Test
    void testUpdate() {
        Student student = Student.builder()
                .id(1)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .build();

        dao.update(student);

        int expected = 1;
        int actual = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "students",
                "student_id = 1 AND first_name = 'UpdatedFirstName' AND last_name = 'UpdatedLastName'");

        assertEquals(expected, actual);
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
        List<Student> expected = new ArrayList<>();
        expected.add(Student.builder().id(1).groupId(1).firstName("Alex").lastName("Williams").build());
        expected.add(Student.builder().id(3).groupId(2).firstName("Leon").lastName("Kennedy").build());

        List<Student> actual = dao.findByCourseName(courseName);

        assertEquals(expected, actual);
    }

    @Test
    void testAddStudentToCourse() {
        int studentId = 2;
        int courseId = 1;

        dao.addToCourse(studentId, courseId);

        int expected = 3;
        int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "student_courses", "course_id = " + courseId);

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

    @Test
    void testGetNumberOfStudentsWhenNullResult() {
        jdbcTemplate.execute("DELETE FROM student_courses");
        jdbcTemplate.execute("DELETE FROM students");

        int expected = 0;
        int actual = dao.getTotalNumber();

        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdWhenStudentExists() {
        int studentId = 1;

        Optional<Student> optional = dao.findById(studentId);
        Student actual = optional.get();

        Student expected = Student.builder()
                .id(studentId)
                .groupId(1)
                .firstName("Alex")
                .lastName("Williams")
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdWhenStudentDoesNotExist() {
        int student = -1;
        Optional<Student> optional = dao.findById(student);

        assertFalse(optional.isPresent());
    }

}
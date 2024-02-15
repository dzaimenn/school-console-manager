package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcStudentDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentDao dao;

    @BeforeEach
    void setUp() {
        dao = new JdbcStudentDao(jdbcTemplate);
    }

    @Test
    void testGetAll() {
        List<Student> students = dao.getAll();

        assertNotNull(students);
        assertEquals(3, students.size());
    }

    @Test
    void testCreate() {

        Student student = Student.builder()
                .firstName("Alice")
                .lastName("Smith")
                .build();

        dao.create(student);
        List<Student> students = dao.getAll();

        assertNotNull(students);
        assertEquals(4, students.size());
    }

    @Test
    void testUpdate() {

        Student student = Student.builder()
                .studentId(1)
                .firstName("John")
                .lastName("Smith")
                .build();

        dao.update(student);
        Student updatedStudent = dao.findStudentById(1);

        assertNotNull(updatedStudent);
        assertEquals("John", updatedStudent.getFirstName());
        assertEquals("Smith", updatedStudent.getLastName());
    }

    @Test
    void testDeleteById() {
        dao.deleteById(3);

        List<Student> students = dao.getAll();

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    void testFindStudentById() {
        int studentId = 1;
        Student student = dao.findStudentById(studentId);

        assertNotNull(student);
        assertEquals(studentId, student.getStudentId());
    }

    @Test
    void testFindStudentsByCourseName() {
        String courseName = "Java Basics";
        List<Student> students = dao.findStudentsByCourseName(courseName);

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    void testAddStudentToCourse() {
        int studentId = 2;
        int courseId = 1;

        dao.addStudentToCourse(studentId, courseId);
        List<Student> students = dao.findStudentsByCourseName("Java Basics");

        assertNotNull(students);
        assertEquals(3, students.size());
    }

    @Test
    void testRemoveStudentFromCourse() {
        int studentIdToRemoveFromCourse = 1;
        int courseId = 1;

        dao.removeStudentFromCourse(studentIdToRemoveFromCourse, courseId);
        List<Student> students = dao.findStudentsByCourseName("Java Basics");

        assertNotNull(students);
        assertEquals(1, students.size());
    }

    @Test
    void testGetNumberOfStudents() {
        int totalStudents = dao.getNumberOfStudents();

        assertEquals(3, totalStudents);
    }

}
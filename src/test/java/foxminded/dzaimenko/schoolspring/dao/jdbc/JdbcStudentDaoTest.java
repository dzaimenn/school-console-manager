package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.config.DaoConfig;
import foxminded.dzaimenko.schoolspring.config.RowMapperConfig;
import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"classpath:/sql/drop_test_tables.sql",
                "classpath:/sql/create_test_tables.sql",
                "classpath:/sql/insert_test_data.sql"},

        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Import({DaoConfig.class, RowMapperConfig.class})
class JdbcStudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    void testGetAll() {
        List<Student> students = studentDao.getAll();
        assertEquals(3, students.size());
    }

    @Test
    void testCreate() {

        Student student = Student.builder()
                .firstName("Alice")
                .lastName("Smith")
                .build();

        studentDao.create(student);
        List<Student> students = studentDao.getAll();

        assertNotNull(students);
        assertEquals(4, students.size());
    }

    @Test
    void testDeleteById() {
        studentDao.deleteById(3);

        List<Student> students = studentDao.getAll();

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    void testFindStudentsByCourseName() {
        String courseName = "Java Basics";
        List<Student> students = studentDao.findByCourseName(courseName);

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    void testAddStudentToCourse() {
        int studentId = 2;
        int courseId = 1;

        studentDao.addToCourse(studentId, courseId);
        List<Student> students = studentDao.findByCourseName("Java Basics");

        assertNotNull(students);
        assertEquals(3, students.size());
    }

    @Test
    void testRemoveStudentFromCourse() {
        int studentIdToRemoveFromCourse = 1;
        int courseId = 1;

        studentDao.removeFromCourse(studentIdToRemoveFromCourse, courseId);
        List<Student> students = studentDao.findByCourseName("Java Basics");

        assertNotNull(students);
        assertEquals(1, students.size());
    }

    @Test
    void testGetNumberOfStudents() {
        int totalStudents = studentDao.getTotalNumber();

        assertEquals(3, totalStudents);
    }

}
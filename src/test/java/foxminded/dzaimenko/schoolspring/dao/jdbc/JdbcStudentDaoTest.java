package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

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
    private StudentDao studentDao;

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
        List<Student> actual = studentDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {

        Student student = Student.builder()
                .firstName("Alice")
                .lastName("Smith")
                .build();

        studentDao.create(student);
        List<Student> students = studentDao.getAll();

        assertEquals(4, students.size());
    }

    @Test
    void testDeleteById() {
        studentDao.deleteById(3);

        List<Student> students = studentDao.getAll();

        assertEquals(2, students.size());
    }

    @Test
    void testFindStudentsByCourseName() {
        String courseName = "Java Basics";
        List<Student> students = studentDao.findByCourseName(courseName);

        assertEquals(2, students.size());
    }

    @Test
    void testAddStudentToCourse() {
        int studentId = 2;
        int courseId = 1;

        studentDao.addToCourse(studentId, courseId);
        List<Student> students = studentDao.findByCourseName("Java Basics");

        assertEquals(3, students.size());
    }

    @Test
    void testRemoveStudentFromCourse() {
        int studentIdToRemoveFromCourse = 1;
        int courseId = 1;

        studentDao.removeFromCourse(studentIdToRemoveFromCourse, courseId);
        List<Student> students = studentDao.findByCourseName("Java Basics");

        assertEquals(1, students.size());
    }

    @Test
    void testGetNumberOfStudents() {
        int totalStudents = studentDao.getTotalNumber();

        assertEquals(3, totalStudents);
    }

}
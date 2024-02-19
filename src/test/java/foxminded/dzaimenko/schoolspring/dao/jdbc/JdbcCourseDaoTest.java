package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.model.Course;
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
class JdbcCourseDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CourseDao dao;

    @BeforeEach
    void setUp() {
        dao = new JdbcCourseDao(jdbcTemplate);
    }

    @Test
    void testGetAll() {
        List<Course> courses = dao.getAll();
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
    }

    @Test
    void testCreate() {
        Course course = new Course();

        course.setName("Test Course");
        course.setDescription("Test Course Description");

        dao.create(course);
        List<Course> courses = dao.getAll();

        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertTrue(courses.stream().anyMatch(c -> c.getName().equals("Test Course")));
    }

    @Test
    void testUpdate() {
        Course course = new Course();
        course.setId(1);
        course.setName("Updated Course Name");
        course.setDescription("Updated Course Description");

        dao.update(course);

        List<Course> allCourses = dao.getAll();

        Course updatedCourse = allCourses.stream()
                .filter(c -> c.getId() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(updatedCourse);
        assertEquals("Updated Course Name", updatedCourse.getName());
        assertEquals("Updated Course Description", updatedCourse.getDescription());
    }

    @Test
    void testDelete() {
        int initialSize = dao.getAll().size();
        dao.deleteById(1);

        int finalSize = dao.getAll().size();

        assertEquals(initialSize - 1, finalSize);
    }

}
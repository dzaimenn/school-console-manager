package foxminded.dzaimenko.schoolspring.dao.impl;

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

        course.setCourseName("Test Course");
        course.setCourseDescription("Test Course Description");

        dao.create(course);
        List<Course> courses = dao.getAll();

        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertTrue(courses.stream().anyMatch(c -> c.getCourseName().equals("Test Course")));
    }

    @Test
    void testUpdate() {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("Updated Course Name");
        course.setCourseDescription("Updated Course Description");

        dao.update(course);

        List<Course> allCourses = dao.getAll();

        Course updatedCourse = allCourses.stream()
                .filter(c -> c.getCourseId() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(updatedCourse);
        assertEquals("Updated Course Name", updatedCourse.getCourseName());
        assertEquals("Updated Course Description", updatedCourse.getCourseDescription());
    }

    @Test
    void testDelete() {
        int initialSize = dao.getAll().size();
        dao.deleteById(1);

        int finalSize = dao.getAll().size();

        assertEquals(initialSize - 1, finalSize);
    }

}
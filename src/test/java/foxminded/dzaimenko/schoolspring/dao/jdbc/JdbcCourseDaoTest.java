package foxminded.dzaimenko.schoolspring.dao.jdbc;


import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"classpath:/sql/drop_test_tables.sql",
                "classpath:/db/migration/V1__Create_Tables.sql",
                "classpath:/sql/insert_test_data.sql"},

        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@ComponentScan(basePackages = "foxminded.dzaimenko.schoolspring.dao")
class JdbcCourseDaoTest {

    @Autowired
    private CourseDao dao;

    private List<Course> prepareExpectedCourses() {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(Course.builder().id(1).name("Java Basics").description("Introduction to Java programming").build());
        expectedCourses.add(Course.builder().id(2).name("Databases").description("Introduction to databases").build());
        return expectedCourses;
    }

    @Test
    void testGetAll() {
        List<Course> expected = prepareExpectedCourses();
        List<Course> actual = dao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {
        Course course = new Course();

        course.setName("Test Course");
        course.setDescription("Test Course Description");

        dao.create(course);
        List<Course> courses = dao.getAll();

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
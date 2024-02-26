package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.model.Course;
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
class JdbcCourseDaoTest {

    @Autowired
    private CourseDao dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        Course course = Course.builder()
                .name("Test Course")
                .description("Test Course Description")
                .build();

        dao.create(course);

        int expected = 1;
        int actualName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "courses",
                "course_name = 'Test Course'");

        int actualDescription = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "courses",
                "course_description = 'Test Course Description'");

        assertEquals(expected, actualName);
        assertEquals(expected, actualDescription);

    }

    @Test
    void testUpdate() {
        Course course = Course.builder()
                .id(1)
                .name("Updated Course Name")
                .description("Updated Course Description")
                .build();

        dao.update(course);

        int expected = 1;
        int actualName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "courses",
                "course_id = 1 AND course_name = 'Updated Course Name'");

        int actualDescription = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "courses",
                "course_id = 1 AND course_description = 'Updated Course Description'");

        assertEquals(expected, actualName);
        assertEquals(expected, actualDescription);
    }

    @Test
    void testDelete() {
        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses") - 1;

        dao.deleteById(1);
        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses");

        assertEquals(expected, actual);
    }

}
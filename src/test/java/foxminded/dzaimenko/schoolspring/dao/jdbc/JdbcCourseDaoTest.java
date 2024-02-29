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
class JdbcCourseDaoTest {

    @Autowired
    private CourseDao dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testGetAll() {
        List<Course> expected = new ArrayList<>();
        expected.add(Course.builder().id(1).name("Java Basics").description("Introduction to Java programming").build());
        expected.add(Course.builder().id(2).name("Databases").description("Introduction to databases").build());

        List<Course> actual = dao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {
        Course course = Course.builder()
                .name("NewName")
                .description("NewDescription")
                .build();

        dao.create(course);

        int expected = 1;
        int actual = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "courses",
                "course_name = 'NewName' AND course_description = 'NewDescription'");

        assertEquals(expected, actual);

    }

    @Test
    void testUpdate() {
        Course course = Course.builder()
                .id(1)
                .name("UpdatedName")
                .description("UpdatedDescription")
                .build();

        dao.update(course);

        int expected = 1;
        int actual = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "courses",
                "course_id = 1 AND course_name = 'UpdatedName' AND course_description = 'UpdatedDescription'");

        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdWhenCourseExists() {
        int courseId = 1;

        Optional<Course> optional = dao.findById(courseId);
        Course actual = optional.get();

        Course expected = Course.builder()
                .id(courseId)
                .name("Java Basics")
                .description("Introduction to Java programming")
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdWhenCourseDoesNotExist() {
        int courseId = -1;
        Optional<Course> optional = dao.findById(courseId);

        assertFalse(optional.isPresent());
    }

    @Test
    void testDelete() {
        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses") - 1;

        dao.deleteById(1);
        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses");

        assertEquals(expected, actual);
    }

}
package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.CourseRowMapper;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCourseDao implements CourseDao {

    private final JdbcTemplate jdbcTemplate;
    private final CourseRowMapper courseRowMapper;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseRowMapper = courseRowMapper;
    }

    private static final String SQL_SELECT_ALL_COURSES = "SELECT * FROM courses";

    private static final String SQL_INSERT_COURSE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";

    private static final String SQL_UPDATE_COURSE = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";

    private static final String SQL_SELECT_COURSE_BY_ID = "SELECT * FROM courses WHERE course_id = ?";

    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE course_id = ?";

    private static final String SQL_DELETE_STUDENT_COURSE_BY_ID = "DELETE FROM student_courses WHERE course_id = ?";

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_COURSES, courseRowMapper);
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(SQL_INSERT_COURSE, course.getName(), course.getDescription());
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_COURSE, course.getName(), course.getDescription(), course.getId());
    }

    @Override
    public Optional<Course> findById(int id) {
        Course course = jdbcTemplate.queryForObject(SQL_SELECT_COURSE_BY_ID, courseRowMapper, id);
        return Optional.ofNullable(course);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(SQL_DELETE_STUDENT_COURSE_BY_ID, id);
        jdbcTemplate.update(SQL_DELETE_COURSE_BY_ID, id);
    }

}
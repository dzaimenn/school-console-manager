package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmapper.CourseRowMapper;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCourseDao implements CourseDao {

    private static final String SQL_SELECT_ALL_COURSES = "SELECT * FROM courses";

    private static final String SQL_CREATE_COURSE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?) RETURNING course_id";

    private static final String SQL_UPDATE_COURSE = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";

    private static final String SQL_SELECT_COURSE_BY_ID = "SELECT * FROM courses WHERE course_id = ?";

    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE course_id = ?";

    private static final String SQL_DELETE_STUDENT_COURSE_BY_ID = "DELETE FROM student_courses WHERE course_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final CourseRowMapper courseRowMapper;

    public JdbcCourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseRowMapper = courseRowMapper;
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_COURSES, courseRowMapper);
    }

    @Override
    public void create(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_CREATE_COURSE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            course.setId(key.intValue());
        } else {
            throw new RuntimeException("Failed to retrieve generated key");
        }
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_COURSE, course.getName(), course.getDescription(), course.getId());
    }

    @Override
    public Optional<Course> findById(int id) {
        return DataAccessUtils.optionalResult(jdbcTemplate.query(SQL_SELECT_COURSE_BY_ID, courseRowMapper, id));
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(SQL_DELETE_STUDENT_COURSE_BY_ID, id);
        jdbcTemplate.update(SQL_DELETE_COURSE_BY_ID, id);
    }

}
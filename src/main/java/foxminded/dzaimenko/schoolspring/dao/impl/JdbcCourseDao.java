package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCourseDao implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_COURSE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL_COURSES = "SELECT * FROM courses";
    private static final String SQL_UPDATE_COURSE = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE course_id = ?";

    public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(SQL_INSERT_COURSE, course.getCourseName(), course.getCourseDescription());
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_COURSES, BeanPropertyRowMapper.newInstance(Course.class));
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_COURSE, course.getCourseName(), course.getCourseDescription(), course.getCourseId());
    }

    @Override
    public void deleteById(int courseId) {
        jdbcTemplate.update(SQL_DELETE_COURSE_BY_ID, courseId);
    }

}
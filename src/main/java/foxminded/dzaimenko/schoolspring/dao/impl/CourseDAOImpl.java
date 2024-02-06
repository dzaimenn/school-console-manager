package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.CourseDAO;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDAOImpl implements CourseDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_COURSE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL_COURSES = "SELECT * FROM courses";
    private static final String SQL_UPDATE_COURSE = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE course_id = ?";

    public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createCourse(Course course) {
        jdbcTemplate.update(SQL_INSERT_COURSE, course.getCourseName(), course.getCourseDescription());
    }

    @Override
    public List<Course> getAllCourses() {
        return jdbcTemplate.query(SQL_SELECT_ALL_COURSES, BeanPropertyRowMapper.newInstance(Course.class));
    }

    @Override
    public void updateCourse(Course course) {
        jdbcTemplate.update(SQL_UPDATE_COURSE, course.getCourseName(), course.getCourseDescription(), course.getCourseId());
    }

    @Override
    public void deleteCourseById(int courseId) {
        jdbcTemplate.update(SQL_DELETE_COURSE_BY_ID, courseId);
    }

}
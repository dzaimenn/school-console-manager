package foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers;

import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Course.builder()
                .id(rs.getInt("course_id"))
                .name(rs.getString("course_name"))
                .description(rs.getString("course_description"))
                .build();
    }

}
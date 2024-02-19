package foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers;

import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Student.builder()
                .studentId(rs.getInt("student_id"))
                .groupId(rs.getInt("group_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }

}
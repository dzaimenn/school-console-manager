package foxminded.dzaimenko.schoolspring.dao.rowmapper;

import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class StudentRowMapper implements RowMapper<Student> {


    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

        Student student = new Student();

        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));

        int studentId = rs.getInt("student_id");
        student.setStudentId(studentId);

        if (columnExists(rs)) {
            int groupId = rs.getInt("group_id");
            student.setGroupId(groupId);
        }

        return student;
    }

    private boolean columnExists(ResultSet rs) {
        try {
            rs.findColumn("group_id");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
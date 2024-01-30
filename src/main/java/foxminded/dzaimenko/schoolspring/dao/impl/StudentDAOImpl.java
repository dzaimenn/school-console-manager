package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Student> findStudentsByCourseName(String course) {

        String sqlFindStudentsByCourse = """
                SELECT students.student_id, students.first_name, students.last_name
                FROM students
                JOIN student_courses ON students.student_id = student_courses.student_id
                JOIN courses ON student_courses.course_id = courses.course_id
                WHERE courses.course_name = ?
                """;

        return jdbcTemplate.query(sqlFindStudentsByCourse, new Object[]{course}, new BeanPropertyRowMapper<>(Student.class));
    }


    public void addNewStudent(String firstName, String lastName) {

        String sqlAddNewStudent = """
                INSERT INTO students (first_name, last_name)
                VALUES (?,?);
                """;

        jdbcTemplate.update(sqlAddNewStudent, firstName, lastName);
    }


    public void deleteStudentById(int studentId) {

        String sqlDeleteStudentById = """
                WITH deleted_student_courses AS (
                    DELETE FROM student_courses
                    WHERE student_id = ?
                    RETURNING *
                )
                DELETE FROM students
                WHERE student_id = ?;;
                """;

        jdbcTemplate.update(sqlDeleteStudentById, studentId);
    }

    public void addStudentToCourse(int studentId, int courseId) {

        String sqlAddStudentToCourse = """
                INSERT INTO student_courses (student_id, course_id)
                VALUES (?, ?);
                """;

        jdbcTemplate.update(sqlAddStudentToCourse, studentId, courseId);

    }

    public void removeStudentFromCourse(int idStudentToRemoveFromCourse, int idCourse) {

        String sqlRemoveStudentFromCourse = """
                DELETE FROM student_courses
                WHERE student_id = ? AND course_id = ?;
                """;

        jdbcTemplate.update(sqlRemoveStudentFromCourse, idStudentToRemoveFromCourse, idCourse);
    }

}

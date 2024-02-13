package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcStudentDao implements StudentDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_STUDENT_BY_ID = "SELECT * FROM students WHERE student_id = ?";

    private static final String SQL_FIND_STUDENTS_BY_COURSE = """
            SELECT students.student_id, students.first_name, students.last_name
            FROM students
            JOIN student_courses ON students.student_id = student_courses.student_id
            JOIN courses ON student_courses.course_id = courses.course_id
            WHERE courses.course_name = ?
            """;

    private static final String SQL_ADD_NEW_STUDENT = """
            INSERT INTO students (first_name, last_name)
            VALUES (?,?);
            """;

    private static final String SQL_DELETE_STUDENT_BY_ID = """
            WITH deleted_student_courses AS (
                DELETE FROM student_courses
                WHERE student_id = ?
                RETURNING *
            )
            DELETE FROM students
            WHERE student_id = ?;
            """;

    private static final String SQL_ADD_STUDENT_TO_COURSE = """
            INSERT INTO student_courses (student_id, course_id)
            VALUES (?, ?);
            """;

    private static final String SQL_REMOVE_STUDENT_FROM_COURSE = """
            DELETE FROM student_courses
            WHERE student_id = ? AND course_id = ?;
            """;

    private static final String SQL_SELECT_ALL_STUDENTS = "SELECT * FROM students";

    private static final String SQL_GET_NUMBER_OF_STUDENTS = "SELECT COUNT(*) AS total_students FROM students";

    public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student findStudentById(int studentId) {
        return jdbcTemplate.queryForObject(SQL_FIND_STUDENT_BY_ID, new Object[]{studentId}, BeanPropertyRowMapper.newInstance(Student.class));
    }

    @Override
    public List<Student> findStudentsByCourseName(String course) {
        return jdbcTemplate.query(SQL_FIND_STUDENTS_BY_COURSE, new Object[]{course}, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void addNewStudent(String firstName, String lastName) {
        jdbcTemplate.update(SQL_ADD_NEW_STUDENT, firstName, lastName);
    }

    @Override
    public void deleteStudentById(int studentId) {
        jdbcTemplate.update(SQL_DELETE_STUDENT_BY_ID, studentId);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        jdbcTemplate.update(SQL_ADD_STUDENT_TO_COURSE, studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(int idStudentToRemoveFromCourse, int idCourse) {
        jdbcTemplate.update(SQL_REMOVE_STUDENT_FROM_COURSE, idStudentToRemoveFromCourse, idCourse);
    }
    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query(SQL_SELECT_ALL_STUDENTS, BeanPropertyRowMapper.newInstance(Student.class));
    }
    @Override
    public int getNumberOfStudents() {
        int totalStudents;

        Integer result = jdbcTemplate.queryForObject(SQL_GET_NUMBER_OF_STUDENTS, Integer.class);

        if (result != null) {
            totalStudents = result;
        } else {
            totalStudents = 0;
        }
        return totalStudents;
    }

}
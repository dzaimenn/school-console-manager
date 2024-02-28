package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmapper.StudentRowMapper;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudentDao implements StudentDao {

    private static final String SQL_SELECT_ALL_STUDENTS = "SELECT * FROM students";

    private static final String SQL_CREATE_STUDENT = "INSERT INTO students (group_id, first_name, last_name) VALUES (?,?,?)";

    private static final String SQL_UPDATE_STUDENT = "UPDATE students SET first_name = ?, last_name = ? WHERE student_id = ? RETURNING student_id";

    private static final String SQL_SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE student_id = ?";

    private static final String SQL_DELETE_STUDENT_COURSES_BY_ID = "DELETE FROM student_courses WHERE student_id = ?";

    private static final String SQL_DELETE_STUDENT_BY_ID = "DELETE FROM students WHERE student_id = ?";

    private static final String SQL_FIND_STUDENTS_BY_COURSE = """
            SELECT students.student_id, students.group_id, students.first_name, students.last_name
            FROM students
            JOIN student_courses ON students.student_id = student_courses.student_id
            JOIN courses ON student_courses.course_id = courses.course_id
            WHERE courses.course_name = ?
            """;

    private static final String SQL_ADD_STUDENT_TO_COURSE = """
            INSERT INTO student_courses (student_id, course_id)
            VALUES (?, ?);
            """;

    private static final String SQL_REMOVE_STUDENT_FROM_COURSE = """
            DELETE FROM student_courses
            WHERE student_id = ? AND course_id = ?;
            """;

    private static final String SQL_GET_NUMBER_OF_STUDENTS = "SELECT COUNT(*) AS total_students FROM students";

    private final JdbcTemplate jdbcTemplate;
    private final StudentRowMapper studentRowMapper;

    public JdbcStudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper = studentRowMapper;
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_STUDENTS, studentRowMapper);
    }

    @Override
    public void create(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, student.getGroupId());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            student.setId(key.intValue());
        } else {
            throw new RuntimeException("Failed to retrieve generated key");
        }
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(SQL_UPDATE_STUDENT, student.getFirstName(), student.getLastName(), student.getId());
    }

    @Override
    public Optional<Student> findById(int id) {
        Student student = jdbcTemplate.queryForObject(SQL_SELECT_STUDENT_BY_ID, studentRowMapper, id);
        return Optional.ofNullable(student);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(SQL_DELETE_STUDENT_COURSES_BY_ID, id);
        jdbcTemplate.update(SQL_DELETE_STUDENT_BY_ID, id);
    }

    @Override
    public List<Student> findByCourseName(String course) {
        return jdbcTemplate.query(SQL_FIND_STUDENTS_BY_COURSE, new Object[]{course}, studentRowMapper);
    }

    @Override
    public void addToCourse(int studentId, int courseId) {
        jdbcTemplate.update(SQL_ADD_STUDENT_TO_COURSE, studentId, courseId);
    }

    @Override
    public void removeFromCourse(int studentId, int courseId) {
        jdbcTemplate.update(SQL_REMOVE_STUDENT_FROM_COURSE, studentId, courseId);
    }

    @Override
    public int getTotalNumber() {
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
package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private final Connection connection;

    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    public List<Student> findStudentsByCourseName(String course) {

        List<Student> students = new ArrayList<>();

        String sqlFindStudentsByCourse = """
                SELECT students.student_id, students.first_name, students.last_name
                FROM students
                JOIN student_courses ON students.student_id = student_courses.student_id
                JOIN courses ON student_courses.course_id = courses.course_id
                WHERE courses.course_name = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sqlFindStudentsByCourse)) {
            ps.setString(1, course);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String studentFirstName = rs.getString("first_name");
                    String studentLastName = rs.getString("last_name");

                    Student student = Student.builder()
                            .firstName(studentFirstName)
                            .lastName(studentLastName)
                            .build();

                    students.add(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }


    public void addNewStudent(String firstName, String lastName) {

        String sqlAddNewStudent = """
                INSERT INTO students (first_name, last_name)
                VALUES (?,?);
                """;

        try (PreparedStatement ps = connection.prepareStatement(sqlAddNewStudent)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        try (PreparedStatement ps = connection.prepareStatement(sqlDeleteStudentById)) {
            ps.setInt(1, studentId);
            ps.setInt(2, studentId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addStudentToCourse(int studentId, int courseId) {

        String sqlAddStudentToCourse = """
                INSERT INTO student_courses (student_id, course_id)
                VALUES (?, ?);
                """;

        try (PreparedStatement ps = connection.prepareStatement(sqlAddStudentToCourse)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeStudentFromCourse(int idStudentToRemoveFromCourse, int idCourse) {

        String sqlRemoveStudentFromCourse = """
                DELETE FROM student_courses
                WHERE student_id = ? AND course_id = ?;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sqlRemoveStudentFromCourse)) {
            ps.setInt(1, idStudentToRemoveFromCourse);
            ps.setInt(2, idCourse);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Student;

import java.util.List;

public interface StudentDao {

    Student findStudentById(int studentId);

    List<Student> findStudentsByCourseName(String course);

    void addNewStudent(String firstname, String lastName);

    void deleteStudentById(int iD);

    void addStudentToCourse(int idStudentToAddToCourse, int idCourse);

    void removeStudentFromCourse(int idStudentToRemoveFromCourse, int idCourse);

    List<Student> getAllStudents();

    int getNumberOfStudents();
}
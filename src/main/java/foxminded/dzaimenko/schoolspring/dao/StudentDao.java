package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {

    Student findStudentById(int studentId);

    List<Student> findStudentsByCourseName(String course);

    void addStudentToCourse(int idStudentToAddToCourse, int idCourse);

    void removeStudentFromCourse(int idStudentToRemoveFromCourse, int idCourse);

    int getNumberOfStudents();

}
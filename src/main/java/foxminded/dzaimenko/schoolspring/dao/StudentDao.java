package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {

    List<Student> findByCourseName(String course);

    void addToCourse(int idStudentToAddToCourse, int idCourse);

    void removeFromCourse(int idStudentToRemoveFromCourse, int idCourse);

    int getTotalNumber();

}
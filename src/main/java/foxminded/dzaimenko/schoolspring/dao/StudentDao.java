package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {

    List<Student> findByCourseName(String course);

    void addToCourse(int studentId, int courseId);

    void removeFromCourse(int studentId, int courseId);

    int getTotalNumber();

}
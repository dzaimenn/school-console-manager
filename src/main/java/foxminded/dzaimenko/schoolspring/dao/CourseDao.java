package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Course;

import java.util.List;

public interface CourseDao {

    void createCourse(Course course);

    List<Course> getAllCourses();

    void updateCourse(Course course);

    void deleteCourseById(int courseId);
}
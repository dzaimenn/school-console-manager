package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class CourseSubMenuImpl implements SubMenu {
    private final String COURSE_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Show all courses
            2. Create a new course
            3. Update course information
            4. Delete a course by ID
            0. Return to Main Menu
            """;

    private final Scanner scanner = new Scanner(System.in);
    private final CourseDao courseDAO;

    public CourseSubMenuImpl(CourseDao courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void displayMenu() {
        while (true) {
            System.out.println(COURSE_MENU_REQUEST);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllCourses();
                    break;
                case 2:
                    createCourse();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

    private void showAllCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void createCourse() {
        System.out.println("Enter the name of the new course:");
        String name = scanner.nextLine();

        System.out.println("Enter the description of the new course:");
        String description = scanner.nextLine();

        Course newCourse = Course.builder()
                .courseName(name)
                .courseDescription(description)
                .build();

        courseDAO.createCourse(newCourse);

        System.out.println("New course created successfully.");
    }

    private void updateCourse() {
        System.out.println("Enter the ID of the course you want to update:");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the new name of the course:");
        String newName = scanner.nextLine();

        System.out.println("Enter the new description of the course:");
        String newDescription = scanner.nextLine();

        Course updatedCourse = Course.builder()
                .courseId(courseId)
                .courseName(newName)
                .courseDescription(newDescription)
                .build();

        courseDAO.updateCourse(updatedCourse);

        System.out.println("Course information updated successfully.");
    }

    private void deleteCourse() {
        System.out.println("Enter the ID of the course you want to delete:");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        courseDAO.deleteCourseById(courseId);

        System.out.println("Course with ID " + courseId + " deleted successfully.");
    }

}
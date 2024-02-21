package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class CourseSubMenuImpl implements SubMenu {
    private final String COURSE_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Show all courses
            2. Create a new course
            3. Update course information
            4. Find a course by ID
            5. Delete a course by ID
                        
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
                    findCourseById();
                    break;
                case 5:
                    deleteCourseById();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

    private void showAllCourses() {
        List<Course> courses = courseDAO.getAll();
        for (Course course : courses) {
            System.out.println(course.getId() + ". " + course.getName() + " - " + course.getDescription());
        }
    }

    private void createCourse() {
        System.out.println("Enter the name of the new course:");
        String name = scanner.nextLine();

        System.out.println("Enter the description of the new course:");
        String description = scanner.nextLine();

        Course newCourse = Course.builder().name(name).description(description).build();

        courseDAO.create(newCourse);

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

        Course updatedCourse = Course.builder().id(courseId).name(newName).description(newDescription).build();

        courseDAO.update(updatedCourse);

        System.out.println("Course information updated successfully.");
    }

    private void findCourseById() {
        System.out.println("Enter course ID");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        Optional<Course> optionalCourse = courseDAO.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            System.out.println("ID: " + courseId + ". Course: " + course.getName() + " - " + course.getDescription());
        } else {
            System.out.println("Course with ID " + courseId + " not found.");
        }
    }

    private void deleteCourseById() {
        System.out.println("Enter the ID of the course you want to delete:");
        int courseIdToDelete = scanner.nextInt();
        scanner.nextLine();

        courseDAO.deleteById(courseIdToDelete);

        System.out.println("Course with ID " + courseIdToDelete + " deleted successfully.");
    }

}
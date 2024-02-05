package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.model.Student;

import java.util.List;
import java.util.Scanner;

public class StudentSubMenuImpl implements SubMenu {
    private final String STUDENT_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Show all students
            2. Find students by ID
            3. Find students related to a course
            4. Add a new student
            5. Delete a student by ID
            6. Add a student to a course
            7. Remove a student from a course
            8. Total number of students
            0. Return to Main Menu
            """;
    private final Scanner scanner;
    private final StudentDAO studentDAO;

    public StudentSubMenuImpl(Scanner scanner, StudentDAO studentDAO) {
        this.scanner = scanner;
        this.studentDAO = studentDAO;
    }

    public void displayMenu() {
        while (true) {
            System.out.println(STUDENT_MENU_REQUEST);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllStudents();
                    break;
                case 2:
                    findStudentById();
                    break;
                case 3:
                    findStudentsByCourse();
                    break;
                case 4:
                    addNewStudent();
                    break;
                case 5:
                    deleteStudentById();
                    break;
                case 6:
                    addStudentToCourse();
                    break;
                case 7:
                    removeStudentFromCourse();
                    break;
                case 8:
                    showTotalNumberOfStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please enter a valid number.");
            }
        }
    }

    private void showAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void findStudentById() {
        System.out.println("Enter the ID of the student:");
        int id = scanner.nextInt();

        Student student = studentDAO.findStudentById(id);
        System.out.println(student != null ? student : "Student not found.");
    }

    private void findStudentsByCourse() {
        System.out.println("Enter the name of the course:");
        String courseName = scanner.nextLine();

        List<Student> students = studentDAO.findStudentsByCourseName(courseName);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void addNewStudent() {
        System.out.println("Enter the first name of the student:");
        String firstName = scanner.nextLine();

        System.out.println("Enter the last name of the student:");
        String lastName = scanner.nextLine();

        studentDAO.addNewStudent(firstName, lastName);
        System.out.println("Student added successfully.");
    }

    private void deleteStudentById() {
        System.out.println("Enter the ID of the student to delete:");
        int id = scanner.nextInt();

        studentDAO.deleteStudentById(id);
        System.out.println("Student deleted successfully.");
    }

    private void addStudentToCourse() {
        System.out.println("Enter the ID of the student:");
        int studentId = scanner.nextInt();

        System.out.println("Enter the ID of the course:");
        int courseId = scanner.nextInt();

        studentDAO.addStudentToCourse(studentId, courseId);
        System.out.println("Student added to the course successfully.");
    }

    private void removeStudentFromCourse() {
        System.out.println("Enter the ID of the student:");
        int studentId = scanner.nextInt();

        System.out.println("Enter the ID of the course:");
        int courseId = scanner.nextInt();

        studentDAO.removeStudentFromCourse(studentId, courseId);
        System.out.println("Student removed from the course successfully.");
    }

    private void showTotalNumberOfStudents() {
        int totalStudents = studentDAO.getNumberOfStudents();
        System.out.println("Total number of students: " + totalStudents);
    }

}
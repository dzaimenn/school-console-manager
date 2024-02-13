package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class StudentSubMenuImpl implements SubMenu {
    private final String STUDENT_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Show all students
            2. Create a new student
            3. Update student information
            4. Delete a student by ID
            5. Find students by ID
            6. Find students related to a course
            7. Add a student to a course
            8. Remove a student from a course
            9. Total number of students
            
            0. Return to Main Menu
            """;
    private final Scanner scanner = new Scanner(System.in);
    private final StudentDao studentDAO;

    public StudentSubMenuImpl(StudentDao studentDAO) {
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
                    createNewStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudentById();
                    break;
                case 5:
                    findStudentById();
                    break;
                case 6:
                    findStudentsByCourse();
                    break;
                case 7:
                    addStudentToCourse();
                    break;
                case 8:
                    removeStudentFromCourse();
                    break;
                case 9:
                    showTotalNumberOfStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

    private void showAllStudents() {
        List<Student> students = studentDAO.getAll();
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

    private void createNewStudent() {
        System.out.println("Enter the first name of the student:");
        String firstName = scanner.nextLine();

        System.out.println("Enter the last name of the student:");
        String lastName = scanner.nextLine();

        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        studentDAO.create(student);
        System.out.println("Student added successfully.");
    }

    private void updateStudent() {
        System.out.println("Enter the ID of the student you want to update:");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the new firstname of the student:");
        String newFirstName = scanner.nextLine();

        System.out.println("Enter the new lastname of the student:");
        String newLastName = scanner.nextLine();

        Student updatedStudent = Student.builder()
                .studentId(studentId)
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();

        studentDAO.update(updatedStudent);

        System.out.println("Student information updated successfully.");
    }

    private void deleteStudentById() {
        System.out.println("Enter the ID of the student to delete:");
        int id = scanner.nextInt();

        studentDAO.deleteById(id);
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
package foxminded.dzaimenko.schoolspring.menu;

import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class StudentSubMenu {
    private final String STUDENT_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Show all students
            2. Create a new student
            3. Update student information
            4. Find students by ID
            5. Delete a student by ID
            6. Find students related to a course
            7. Add a student to a course
            8. Remove a student from a course
            9. Total number of students
                        
            0. Return to Main Menu
            """;
    private final Scanner scanner = new Scanner(System.in);

    private final StudentDao studentDao;

    public StudentSubMenu(StudentDao studentDao) {
        this.studentDao = studentDao;
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
                    findStudentById();
                    break;
                case 5:
                    deleteStudentById();
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
        List<Student> students = studentDao.getAll();
        for (Student student : students) {
            System.out.println(student.getId() + ". " + student.getFirstName() + " " + student.getLastName());
        }
    }

    private void createNewStudent() {
        System.out.println("Enter the first name of the student:");
        String firstName = scanner.nextLine();

        System.out.println("Enter the last name of the student:");
        String lastName = scanner.nextLine();

        System.out.println("EEnter group ID:");
        Integer groupId = scanner.nextInt();

        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .groupId(groupId)
                .build();

        studentDao.create(student);
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
                .id(studentId)
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();

        studentDao.update(updatedStudent);

        System.out.println("Student information updated successfully.");
    }

    private void findStudentById() {
        System.out.println("Enter student ID:");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        Optional<Student> optionalStudent = studentDao.findById(studentId);

        optionalStudent.ifPresentOrElse(
                student -> {
                    System.out.println("Student found:");
                    System.out.println("ID: " + student.getId());
                    System.out.println("First Name: " + student.getFirstName());
                    System.out.println("Last Name: " + student.getLastName());
                    System.out.println("Group ID: " + student.getGroupId());
                },
                () -> System.out.println("Student with ID " + studentId + " not found.")
        );
    }

    private void deleteStudentById() {
        System.out.println("Enter the ID of the student to delete:");
        int id = scanner.nextInt();

        studentDao.deleteById(id);
        System.out.println("Student deleted successfully.");
    }

    private void findStudentsByCourse() {
        System.out.println("Enter the name of the course:");
        String courseName = scanner.nextLine();

        List<Student> students = studentDao.findByCourseName(courseName);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void addStudentToCourse() {
        System.out.println("Enter the ID of the student:");
        int studentId = scanner.nextInt();

        System.out.println("Enter the ID of the course:");
        int courseId = scanner.nextInt();

        studentDao.addToCourse(studentId, courseId);
        System.out.println("Student added to the course successfully.");
    }

    private void removeStudentFromCourse() {
        System.out.println("Enter the ID of the student:");
        int studentId = scanner.nextInt();

        System.out.println("Enter the ID of the course:");
        int courseId = scanner.nextInt();

        studentDao.removeFromCourse(studentId, courseId);
        System.out.println("Student removed from the course successfully.");
    }

    private void showTotalNumberOfStudents() {
        int totalStudents = studentDao.getTotalNumber();
        System.out.println("Total number of students: " + totalStudents);
    }

}
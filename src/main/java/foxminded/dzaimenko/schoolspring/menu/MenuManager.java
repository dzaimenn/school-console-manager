package foxminded.dzaimenko.schoolspring.menu;

import foxminded.dzaimenko.schoolspring.SchoolSpringApplication;
import foxminded.dzaimenko.schoolspring.dao.GroupDAO;
import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.dao.impl.GroupDAOImpl;
import foxminded.dzaimenko.schoolspring.dao.impl.StudentDAOImpl;
import foxminded.dzaimenko.schoolspring.model.Course;
import foxminded.dzaimenko.schoolspring.model.Group;
import foxminded.dzaimenko.schoolspring.model.Student;

import foxminded.dzaimenko.schoolspring.util.SchoolData;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class MenuManager {

    private final String MAIN_MENU_REQUEST = """
            ______________________________________________________________
            Enter a request from 1 to 6 or 0 to complete the job:
            1. Find all groups with less or equal students’ number
            2. Find all students related to the course with the given name
            3. Add a new student
            4. Delete a student by the STUDENT_ID
            5. Add a student to the course (from a list)
            6. Remove the student from one of their courses
            0. Exit
                        
            Enter the number of the selected request:""";


    private final Map<Integer, Runnable> options;
    private Scanner scanner;
    private int totalStudents;

    public MenuManager(Scanner scanner) {
        this.scanner = scanner;
        this.options = new HashMap<>();
    }

    public void displayMainMenu() {
        System.out.println(MAIN_MENU_REQUEST);
    }

    public void requestManagement() {
        int maxRequests = 0;

        while (maxRequests < 4) {

            displayMainMenu();

            try {
                int option = Integer.parseInt(scanner.nextLine());

                if (options.containsKey(option)) {
                    options.get(option).run();
                    maxRequests = 0;

                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 6 or 0 to exit.");
                    maxRequests++;

                }
            } catch (NumberFormatException e) {
                if (maxRequests < 2) {
                    System.out.println("Invalid input. Please enter a valid numeric request. Try again.");

                }
                maxRequests++;
            }

            if (maxRequests == 3) {
                System.out.println("You have entered incorrect instructions multiple times. Exiting the program.");
                break;
            }
        }
    }

    public void menuFindGroupsByMinStudentsCount() {

        int maxStudentCount = 0;
        int attempts = 0;

        while (attempts < 3) {
            System.out.println("Enter the maximum number of students from 1 to " + totalStudents + ":");
            int userInput = scanner.nextInt();

            if (userInput < 1 || userInput > totalStudents) {
                System.out.println("Invalid value");
                attempts++;
            } else {
                maxStudentCount = userInput;
                break;
            }
        }

        if (attempts == 3) {
            System.out.println("Reached maximum attempts. Exiting program.");
            System.exit(0);

        }

        GroupDAO groupDAO = new GroupDAOImpl(jdbcTemplate);
        List<Group> groups = groupDAO.findGroupsWithMaxStudentCount(maxStudentCount);

        if (groups.isEmpty()) {
            System.out.println("No groups found");
            return;
        }

        System.out.println("Groups with " + maxStudentCount + " or fewer students:");
        for (Group group : groups) {
            System.out.println("Group ID: " + group.getGroupId() + ", Group name: " + group.getGroupName());
        }

    }

    public void menuFindStudentsByCourseName() {

        showAllCourses();

        String courseNumberPrompt = "Enter course number:";
        String course = SchoolData.coursesNames[(SchoolSpringApplication.validateNumericInput(scanner, courseNumberPrompt, 1, 10) - 1)];

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        List<Student> students = studentDAO.findStudentsByCourseName(course);

        if (students.isEmpty()) {
            System.out.println("No students found for the given course");
            return;
        }

        System.out.println("Students studying " + course + ":");
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.println((i + 1) + ". " + student.getFirstName() + " " + student.getLastName());
        }
    }

    private void showAllCourses() {
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + ". " + SchoolData.coursesNames[i]);
        }
    }

    public void menuAddNewStudent() {

        System.out.println("Enter the first name of the new student:");
        String firstName = scanner.nextLine();

        System.out.println("Enter the last name of the new student:");
        String lastName = scanner.nextLine();

        StudentDAO student = new StudentDAOImpl(jdbcTemplate);
        student.addNewStudent(firstName, lastName);
    }

    private int getNumberOfStudents() {
        String sqlGetNumberOfStudents = "SELECT COUNT(*) AS total_students FROM students";
        Integer result = jdbcTemplate.queryForObject(sqlGetNumberOfStudents, Integer.class);

        if (result != null) {
            totalStudents = result;
        } else {
            totalStudents = 0;
        }

        return totalStudents;
    }

    public void menuDeleteStudentById() {
        getNumberOfStudents();
        showAllStudents();
        String prompt = "Enter the ID of the student to be removed (from 1 to " + totalStudents + "):";

        System.out.println("The school has " + totalStudents + " students");
        int idStudentToDelete = SchoolSpringApplication.validateNumericInput(scanner, prompt, 1, totalStudents);

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        studentDAO.deleteStudentById(idStudentToDelete);

    }

    private void showAllStudents() {

        StudentDAO students = new StudentDAOImpl(jdbcTemplate);

        for (Student student : students) {
            System.out.println("ID: " + student.getStudentId() + ", Student: " + student.getFirstName() + " " + student.getLastName());
        }
    }

    public void menuAddStudentToCourse() {
        getNumberOfStudents();
        showAllStudents();

        String promptStudentAdd = "Enter the student ID to add to the course:";
        int idStudentToAddToCourse = SchoolSpringApplication.validateNumericInput(scanner, promptStudentAdd, 1, totalStudents);

        String promptCourseAdd = "Enter course number:";
        showAllCourses();
        int idCourse = SchoolSpringApplication.validateNumericInput(scanner, promptCourseAdd, 1, 10);

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        studentDAO.addStudentToCourse(idStudentToAddToCourse, idCourse);
    }

    private void showCoursesForStudent(int studentId) {

        String sqlSelectCourses = """
                SELECT courses.course_id, courses.course_name
                FROM student_courses
                JOIN courses ON student_courses.course_id = courses.course_id
                WHERE student_courses.student_id = ?;
                """;

        List<Course> courses = jdbcTemplate.query(sqlSelectCourses, new Object[]{studentId}, new BeanPropertyRowMapper<>(Course.class));

        for (Course course: courses) {
            System.out.println(course.getCourseId() + ". " + course.getCourseName());
        }
    }

    public void menuRemoveStudentFromCourse() {
        getNumberOfStudents();
        showAllStudents();

        String promptStudentRemove = "Enter the student ID to remove from the course:";
        int idStudentToRemoveFromCourse = SchoolSpringApplication.validateNumericInput(scanner, promptStudentRemove, 1, totalStudents);

        String promptCourseRemove = "Enter course ID:";
        showCoursesForStudent(idStudentToRemoveFromCourse);
        int idCourse = SchoolSpringApplication.validateNumericInput(scanner, promptCourseRemove, 1, 10);

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        studentDAO.removeStudentFromCourse(idStudentToRemoveFromCourse, idCourse);
    }

}
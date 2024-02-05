package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;

import java.util.Scanner;

public class StudentSubMenuImpl implements SubMenu {
    private final String STUDENT_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Find students related to a course
            2. Add a new student
            3. Delete a student by ID
            4. Add a student to a course
            5. Remove a student from a course
            6. Total number of students
            0. Return to Main Menu
            """;
    private Scanner scanner;
    private StudentDAO studentDAO;

    public StudentSubMenuImpl(Scanner scanner, StudentDAO studentDAO) {
        this.scanner = scanner;
        this.studentDAO = studentDAO;
    }

}
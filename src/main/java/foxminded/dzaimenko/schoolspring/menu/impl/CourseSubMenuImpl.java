package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.CourseDAO;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;

import java.util.Scanner;

public class CourseSubMenuImpl implements SubMenu {
    private final String COURSE_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Create a new course
            2. Update course information
            3. Delete a course by ID
            0. Return to Main Menu
            """;

    private Scanner scanner;
    private CourseDAO courseDAO;

    public CourseSubMenuImpl(Scanner scanner, CourseDAO courseDAO) {
        this.scanner = scanner;
        this.courseDAO = courseDAO;
    }

}
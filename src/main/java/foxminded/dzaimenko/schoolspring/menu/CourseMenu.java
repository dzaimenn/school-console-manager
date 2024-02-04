package foxminded.dzaimenko.schoolspring.menu;

import java.util.Scanner;

public class CourseMenu {
    private final String COURSE_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Create a new course
            2. Update course information
            3. Delete a course by ID
            0. Return to Main Menu
            """;

    private Scanner scanner;

    public CourseMenu(Scanner scanner) {
        this.scanner = scanner;
    }

}
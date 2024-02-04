package foxminded.dzaimenko.schoolspring.menu;

import java.util.Scanner;

public class MainMenu {
    private final String MAIN_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Manage Student information
            2. Manage Group information
            3. Manage Course information
            0. Exit
            """;
    private Scanner scanner;
    private StudentMenu studentMenu;
    private GroupMenu groupMenu;
    private CourseMenu courseMenu;

    public MainMenu(Scanner scanner, StudentMenu studentMenu, GroupMenu groupMenu, CourseMenu courseMenu) {
        this.scanner = scanner;
        this.studentMenu = studentMenu;
        this.groupMenu = groupMenu;
        this.courseMenu = courseMenu;
    }

}
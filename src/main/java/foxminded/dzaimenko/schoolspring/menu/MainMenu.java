package foxminded.dzaimenko.schoolspring.menu;

import foxminded.dzaimenko.schoolspring.menu.impl.GroupSubMenuImpl;
import foxminded.dzaimenko.schoolspring.menu.impl.StudentSubMenuImpl;
import foxminded.dzaimenko.schoolspring.menu.impl.CourseSubMenuImpl;

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
    private StudentSubMenuImpl studentSubMenuImpl;
    private GroupSubMenuImpl groupSubMenuImpl;
    private CourseSubMenuImpl courseSubMenuImpl;

    public MainMenu(Scanner scanner, StudentSubMenuImpl studentSubMenuImpl, GroupSubMenuImpl groupSubMenuImpl, CourseSubMenuImpl courseSubMenuImpl) {
        this.scanner = scanner;
        this.studentSubMenuImpl = studentSubMenuImpl;
        this.groupSubMenuImpl = groupSubMenuImpl;
        this.courseSubMenuImpl = courseSubMenuImpl;
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println(MAIN_MENU_REQUEST);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    studentSubMenuImpl.displayMenu();
                    break;
                case 2:
                    groupSubMenuImpl.displayMenu();
                    break;
                case 3:
                    courseSubMenuImpl.displayMenu();
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

}
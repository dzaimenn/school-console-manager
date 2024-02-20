package foxminded.dzaimenko.schoolspring.menu;

import jakarta.annotation.PostConstruct;

import java.util.Scanner;

public class MainMenu {
    private final String MAIN_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Manage Students
            2. Manage Groups
            3. Manage Courses
            
            0. Exit
            """;
    private final Scanner scanner = new Scanner(System.in);
    private final SubMenu studentSubMenu;
    private final SubMenu groupSubMenu;
    private final SubMenu courseSubMenu;

    public MainMenu(SubMenu studentSubMenu, SubMenu groupSubMenu, SubMenu courseSubMenu) {
        this.studentSubMenu = studentSubMenu;
        this.groupSubMenu = groupSubMenu;
        this.courseSubMenu = courseSubMenu;
    }

    @PostConstruct
    public void displayMainMenu() {
        while (true) {
            System.out.println(MAIN_MENU_REQUEST);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    studentSubMenu.displayMenu();
                    break;
                case 2:
                    groupSubMenu.displayMenu();
                    break;
                case 3:
                    courseSubMenu.displayMenu();
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

}
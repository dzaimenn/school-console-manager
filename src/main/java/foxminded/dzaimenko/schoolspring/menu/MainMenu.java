package foxminded.dzaimenko.schoolspring.menu;

import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class MainMenu {
    private final String MAIN_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Manage Student information
            2. Manage Group information
            3. Manage Course information
            0. Exit
            """;
    private Scanner scanner = new Scanner(System.in);
    private SubMenu studentSubMenu;
    private SubMenu groupSubMenu;
    private SubMenu courseSubMenu;

    public MainMenu(SubMenu studentSubMenu, SubMenu groupSubMenu, SubMenu courseSubMenu) {
        this.studentSubMenu = studentSubMenu;
        this.groupSubMenu = groupSubMenu;
        this.courseSubMenu = courseSubMenu;
    }

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
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

}
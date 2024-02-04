package foxminded.dzaimenko.schoolspring.menu;

import foxminded.dzaimenko.schoolspring.dao.GroupDAO;

import java.util.Scanner;

public class GroupMenu {
    private final String GROUP_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Find groups with less or equal students’ number
            2. Create a new group
            3. Update group information
            4. Delete a group by ID
            0. Return to Main Menu
            """;

    private Scanner scanner;
    private GroupDAO groupDAO;

    public GroupMenu(Scanner scanner, GroupDAO groupDAO) {
        this.scanner = scanner;
        this.groupDAO = groupDAO;
    }

}
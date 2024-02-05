package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.GroupDAO;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;

import java.util.Scanner;

public class GroupSubMenuImpl implements SubMenu {
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

    public GroupSubMenuImpl(Scanner scanner, GroupDAO groupDAO) {
        this.scanner = scanner;
        this.groupDAO = groupDAO;
    }

}
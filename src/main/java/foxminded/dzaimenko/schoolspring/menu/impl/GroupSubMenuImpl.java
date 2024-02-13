package foxminded.dzaimenko.schoolspring.menu.impl;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class GroupSubMenuImpl implements SubMenu {
    private final String GROUP_MENU_REQUEST = """
            ______________________________________________________________
            Please select an option (Enter the number):
            1. Show all groups
            2. Create a new group
            3. Update group information
            4. Delete a group by ID
            5. Find groups with less or equal students’ number
                        
            0. Return to Main Menu
            """;

    private final Scanner scanner = new Scanner(System.in);
    private final GroupDao groupDAO;

    public GroupSubMenuImpl(GroupDao groupDAO) {
        this.groupDAO = groupDAO;
    }

    public void displayMenu() {
        while (true) {
            System.out.println(GROUP_MENU_REQUEST);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllGroups();
                    break;
                case 2:
                    createNewGroup();
                    break;
                case 3:
                    updateGroupInformation();
                    break;
                case 4:
                    deleteGroupById();
                    break;
                case 5:
                    findGroupsWithMaxStudentCount();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please enter a valid number:");
            }
        }
    }

    private void showAllGroups() {
        List<Group> groups = groupDAO.getAll();
        for (Group group : groups) {
            System.out.println(group);
        }
    }

    private void createNewGroup() {
        System.out.println("Enter the name of the new group:");
        String groupName = scanner.nextLine();

        Group newGroup = Group.builder()
                .groupName(groupName)
                .build();

        groupDAO.create(newGroup);
        System.out.println("New group created successfully.");
    }

    private void updateGroupInformation() {
        System.out.println("Enter the ID of the group to update:");
        int groupId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the new name for the group:");
        String newGroupName = scanner.nextLine();

        Group updatedGroup = Group.builder()
                .groupId(groupId)
                .groupName(newGroupName)
                .build();

        groupDAO.update(updatedGroup);
        System.out.println("Group information updated successfully.");
    }

    private void deleteGroupById() {
        System.out.println("Enter the ID of the group to delete:");
        int groupId = scanner.nextInt();
        scanner.nextLine();

        groupDAO.deleteById(groupId);
        System.out.println("Group deleted successfully.");
    }

    private void findGroupsWithMaxStudentCount() {
        System.out.println("Enter the maximum number of students:");
        int maxStudentCount = scanner.nextInt();
        scanner.nextLine();

        List<Group> groups = groupDAO.findGroupsWithMaxStudentCount(maxStudentCount);

        if (groups.isEmpty()) {
            System.out.println("No groups found with less or equal students' number.");
        } else {
            System.out.println("Groups with less or equal students' number:");
            for (Group group : groups) {
                System.out.println(group);
            }
        }
    }

}
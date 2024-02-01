package foxminded.dzaimenko.schoolspring;

import foxminded.dzaimenko.schoolspring.config.SpringConfig;
import foxminded.dzaimenko.schoolspring.util.MenuManager;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@SpringBootApplication
@Import(SpringConfig.class)
public class SchoolSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolSpringApplication.class, args);

        ApplicationContext context = SpringApplication.run(SchoolSpringApplication.class, args);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        Scanner scanner = context.getBean(Scanner.class);

        Flyway flyway = context.getBean(Flyway.class);
        flyway.migrate();

        Map<Integer, Runnable> options = new HashMap<>();
        MenuManager menuManager = new MenuManager(jdbcTemplate, options, scanner);

        options.put(1, menuManager::menuFindGroupsByMinStudentsCount);
        options.put(2, menuManager::menuFindStudentsByCourseName);
        options.put(3, menuManager::menuAddNewStudent);
        options.put(4, menuManager::menuDeleteStudentById);
        options.put(5, menuManager::menuAddStudentToCourse);
        options.put(6, menuManager::menuRemoveStudentFromCourse);
        options.put(0, SchoolSpringApplication::shutdown);


        fillDatabase(jdbcTemplate);
        menuManager.requestManagement();

    }

    private static void fillDatabase(JdbcTemplate jdbcTemplate) {
        DatabaseFiller databaseFiller = new DatabaseFiller(jdbcTemplate);
        databaseFiller.fillDataBase();
        System.out.println("Database filled successfully");
    }

    public static int validateNumericInput(Scanner scanner, String prompt, int lowerBound, int upperBound) {

        int maxRequests = 0;
        final int maxAttempts = 3;
        int option = -1;

        while (maxRequests < maxAttempts) {
            System.out.println(prompt);

            try {
                option = Integer.parseInt(scanner.nextLine());

                if (option >= lowerBound && option <= upperBound) {
                    return option;
                } else {
                    System.out.println("Invalid input. Please enter a number within the specified range.");
                    maxRequests++;
                }
            } catch (NumberFormatException e) {
                if (maxRequests < maxAttempts - 1) {
                    System.out.println("Invalid input. Please enter a valid numeric request. Try again.");
                }
                maxRequests++;
            }

            if (maxRequests == maxAttempts) {
                System.out.println("You have entered incorrect instructions multiple times. Exiting the program.");
                System.exit(0);
            }
        }

        return option;
    }

    public static void shutdown() {

        System.out.println("Exiting the program");
        System.exit(0);
    }

}

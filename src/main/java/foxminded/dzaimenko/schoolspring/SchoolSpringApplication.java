package foxminded.dzaimenko.schoolspring;

import foxminded.dzaimenko.schoolspring.config.SpringConfig;
import foxminded.dzaimenko.schoolspring.dao.CourseDAO;
import foxminded.dzaimenko.schoolspring.dao.GroupDAO;
import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.menu.MainMenu;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.menu.impl.CourseSubMenuImpl;
import foxminded.dzaimenko.schoolspring.menu.impl.GroupSubMenuImpl;
import foxminded.dzaimenko.schoolspring.menu.impl.StudentSubMenuImpl;
import foxminded.dzaimenko.schoolspring.util.DatabaseFiller;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Import(SpringConfig.class)
public class SchoolSpringApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SchoolSpringApplication.class, args);

        Flyway flyway = context.getBean(Flyway.class);
        flyway.migrate();

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        DatabaseFiller databaseFiller = new DatabaseFiller(jdbcTemplate);
        databaseFiller.fillDataBase();

        SubMenu courseSubMenu = new CourseSubMenuImpl(context.getBean(CourseDAO.class));
        SubMenu groupSubMenu = new GroupSubMenuImpl(context.getBean(GroupDAO.class));
        SubMenu studentSubMenu = new StudentSubMenuImpl(context.getBean(StudentDAO.class));

        MainMenu mainMenu = new MainMenu(courseSubMenu, groupSubMenu, studentSubMenu);
        mainMenu.displayMainMenu();

    }

}
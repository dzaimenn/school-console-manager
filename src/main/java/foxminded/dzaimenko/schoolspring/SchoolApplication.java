package foxminded.dzaimenko.schoolspring;

import foxminded.dzaimenko.schoolspring.config.JdbcConfig;
import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.dao.StudentDao;
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
@Import(JdbcConfig.class)
public class SchoolApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SchoolApplication.class, args);

        Flyway flyway = context.getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        DatabaseFiller databaseFiller = new DatabaseFiller(jdbcTemplate);
        databaseFiller.fillDataBase();

        SubMenu studentSubMenu = new StudentSubMenuImpl(context.getBean(StudentDao.class));
        SubMenu groupSubMenu = new GroupSubMenuImpl(context.getBean(GroupDao.class));
        SubMenu courseSubMenu = new CourseSubMenuImpl(context.getBean(CourseDao.class));

        MainMenu mainMenu = new MainMenu(studentSubMenu, groupSubMenu, courseSubMenu);
        mainMenu.displayMainMenu();

    }

}
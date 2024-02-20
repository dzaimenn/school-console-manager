package foxminded.dzaimenko.schoolspring.config;

import foxminded.dzaimenko.schoolspring.menu.MainMenu;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.util.DatabaseFiller;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public DatabaseFiller databaseFiller(JdbcTemplate jdbcTemplate) {
        return new DatabaseFiller(jdbcTemplate);
    }

    @Bean
    public MainMenu mainMenu(SubMenu studentSubMenuImpl, SubMenu groupSubMenuImpl, SubMenu courseSubMenuImpl) {
        return new MainMenu(studentSubMenuImpl, groupSubMenuImpl, courseSubMenuImpl);
    }

}
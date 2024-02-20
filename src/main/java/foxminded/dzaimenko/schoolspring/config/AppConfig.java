package foxminded.dzaimenko.schoolspring.config;

import foxminded.dzaimenko.schoolspring.menu.MainMenu;
import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.util.DatabaseFiller;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
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
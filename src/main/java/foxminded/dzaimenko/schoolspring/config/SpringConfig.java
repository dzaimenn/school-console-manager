package foxminded.dzaimenko.schoolspring.config;

import foxminded.dzaimenko.schoolspring.menu.SubMenu;
import foxminded.dzaimenko.schoolspring.util.DatabaseFiller;
import foxminded.dzaimenko.schoolspring.dao.CourseDAO;
import foxminded.dzaimenko.schoolspring.dao.GroupDAO;
import foxminded.dzaimenko.schoolspring.dao.StudentDAO;
import foxminded.dzaimenko.schoolspring.dao.impl.CourseDAOImpl;
import foxminded.dzaimenko.schoolspring.dao.impl.GroupDAOImpl;
import foxminded.dzaimenko.schoolspring.dao.impl.StudentDAOImpl;
import foxminded.dzaimenko.schoolspring.menu.impl.CourseSubMenuImpl;
import foxminded.dzaimenko.schoolspring.menu.impl.GroupSubMenuImpl;
import foxminded.dzaimenko.schoolspring.menu.MainMenu;
import foxminded.dzaimenko.schoolspring.menu.impl.StudentSubMenuImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Random;
import java.util.Scanner;

@Configuration
public class SpringConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CourseDAO courseDAO(JdbcTemplate jdbcTemplate) {
        return new CourseDAOImpl(jdbcTemplate);
    }

    @Bean
    public GroupDAO groupDAO(JdbcTemplate jdbcTemplate) {
        return new GroupDAOImpl(jdbcTemplate);
    }

    @Bean
    public StudentDAO studentDAO(JdbcTemplate jdbcTemplate) {
        return new StudentDAOImpl(jdbcTemplate);
    }

}
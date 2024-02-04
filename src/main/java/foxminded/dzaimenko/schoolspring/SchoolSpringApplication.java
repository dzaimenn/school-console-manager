package foxminded.dzaimenko.schoolspring;

import foxminded.dzaimenko.schoolspring.config.SpringConfig;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
@Import(SpringConfig.class)
public class SchoolSpringApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SchoolSpringApplication.class, args);

        Flyway flyway = context.getBean(Flyway.class);
        flyway.migrate();
    }

}
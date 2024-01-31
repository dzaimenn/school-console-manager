package foxminded.dzaimenko.schoolspring;

import foxminded.dzaimenko.schoolspring.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(SpringConfig.class)
public class SchoolSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolSpringApplication.class, args);


    }
}
package foxminded.dzaimenko.schoolspring.config;

import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.CourseRowMapper;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.GroupRowMapper;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.StudentRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RowMapperConfig {

    @Bean
    public CourseRowMapper courseRowMapper() {
        return new CourseRowMapper();
    }

    @Bean
    public GroupRowMapper groupRowMapper() {
        return new GroupRowMapper();
    }

    @Bean
    public StudentRowMapper studentRowMapper() {
        return new StudentRowMapper();
    }

}
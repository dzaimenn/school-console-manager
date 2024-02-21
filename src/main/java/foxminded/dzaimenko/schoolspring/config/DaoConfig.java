package foxminded.dzaimenko.schoolspring.config;

import foxminded.dzaimenko.schoolspring.dao.CourseDao;
import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.dao.StudentDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.JdbcCourseDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.JdbcGroupDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.JdbcStudentDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.CourseRowMapper;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.GroupRowMapper;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmappers.StudentRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoConfig {

    @Bean
    @Primary
    public CourseDao courseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {
        return new JdbcCourseDao(jdbcTemplate, courseRowMapper);
    }

    @Bean
    @Primary
    public GroupDao groupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
        return new JdbcGroupDao(jdbcTemplate, groupRowMapper);
    }

    @Bean
    @Primary
    public StudentDao studentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
        return new JdbcStudentDao(jdbcTemplate, studentRowMapper);
    }

}